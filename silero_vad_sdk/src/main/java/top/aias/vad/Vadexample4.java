package top.aias.vad;

import ai.djl.Device;
import ai.djl.inference.Predictor;
import ai.djl.modality.audio.Audio;
import ai.djl.modality.audio.AudioFactory;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import org.bytedeco.ffmpeg.global.avutil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.aias.vad.utils.SileroVAD;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Vadexample4 {

    public static void main(String[] args) {
        String input = "D:\\pro\\videoToWav\\test\\111.wav";
        Path path = Paths.get(input);
        Audio audio =
                null;
        try {
            audio = AudioFactory.newInstance()
                    .setChannels(1)
                    .setSampleRate(16000)
                    .setSampleFormat(avutil.AV_SAMPLE_FMT_S16)
                    .fromFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // window_size_samples [512, 1024, 1536] for 16000 sampling_rate
        // window_size_samples = (frame_duration_ms / 10000) * sampling_rate
        // frame_duration_ms = window_size_samples / (sampling_rate / 1000)
        // frame_duration_ms: 512/16 = 32, 1024/16 = 64, 1536/16 = 96
        List<float[]> frames = generateFrames(audio.getData(), 32, 16000);

        SileroVAD vad = new SileroVAD();
        try (ZooModel<NDList, NDList> model = vad.criteria().loadModel();
             Predictor<NDList, NDList> predictor = model.newPredictor();
             NDManager manager = NDManager.newBaseManager(Device.cpu(), "PyTorch")) {

            float test_threshold = 0.5f;

            NDArray h_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);
            NDArray c_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);

            // 初始化索引和两个映射表
            int index = 0;
            Map<Integer, Float> mapS = new LinkedHashMap<>(); // 存储满足阈值的分数
            Map<Integer, Integer> timeInfo = new LinkedHashMap<>(); // 存储对应的索引

            // 遍历音频帧
            for (float[] frame : frames) {
                // 创建音频特征数组，并转换为float32类型
                NDArray audioFeature = manager.create(frame).reshape(1, frame.length).toType(DataType.FLOAT32, true);
                // 创建采样率数组，并转换为int64类型
                NDArray sampling_rate = manager.create(new int[]{16000}).toType(DataType.INT64, true);
                // 创建NDList，包含音频特征、采样率、h_ort和c_ort
                NDList list = new NDList(audioFeature, sampling_rate, h_ort, c_ort);

                // 使用预测器进行预测
                NDList result = predictor.predict(list);

                // 获取预测结果
                NDArray output = result.get(0);
                // 获取分数
                float score = output.toFloatArray()[0];

                // 如果分数大于等于阈值，则添加到mapS中
                if (score >= test_threshold) {
                    mapS.put(index, score);
                }

                index++;
                h_ort = result.get(1);
                c_ort = result.get(2);
            }

            //遍历map集合，获取index
            Iterator<Integer> iterator = mapS.keySet().iterator();
            int[] arrIndex = new int[mapS.size()];//将index存入数组
            int x = 0;
            while (iterator.hasNext()) {
                Integer key = iterator.next();
                arrIndex[x] = key;
                x++;
            }


            for (int d = 0; d < arrIndex.length - 1; d++) {
                System.out.print(arrIndex[d] + " ");
                if (arrIndex[d + 1] - arrIndex[d] > 1) {
                    System.out.print("*");
                }
                if (d % 20 == 0)
                    System.out.println();
            }
            int d5 = 0;
            int d10 = 0;
            int d20 = 0;
            int d30 = 0;
            int d__ = 0;
            for (int d = 0; d < arrIndex.length - 1; d++) {
                if (arrIndex[d + 1] - arrIndex[d] < 3) {
                    d5++;
                } else if (arrIndex[d + 1] - arrIndex[d] < 10) {
                    d10++;
                } else if (arrIndex[d + 1] - arrIndex[d] < 20) {
                    d20++;
                } else if (arrIndex[d + 1] - arrIndex[d] < 30) {
                    d30++;
                } else {
                    d__++;
                }
            }
            System.out.println("d5: " + d5);
            System.out.println("d10: " + d10);
            System.out.println("d20: " + d20);
            System.out.println("d30: " + d30);
            System.out.println("d40: " + d__);

            //存储索引范围
            int min = 1400;
//            for (int i = 0; i < arrIndex.length - 1; i++) {
//                for (int j = i + 1; j < arrIndex.length; j++) {
//                    if (arrIndex[j - 1] + 1 == arrIndex[j] && arrIndex[j] - arrIndex[i] >= min) {//连续帧，断开
//                        timeInfo.put(arrIndex[i], arrIndex[j]);
//                        i = j;
//                        break;
//                    } else if (Objects.equals(j, arrIndex.length - 1)) {
//                        timeInfo.put(arrIndex[i], arrIndex[j]);
//                        i = j;
//                        break;
//                    }
//                }
//        }


            for (int i = 0; i < arrIndex.length - 1; i++) {
                for (int j = i + 1; j < arrIndex.length; j++) {
                    if ((arrIndex[j] + 1) == arrIndex[j]) {//连续帧，但最大不过1400
                        if ((arrIndex[j] - arrIndex[i]) >= 1400) {
                            timeInfo.put(arrIndex[i], arrIndex[j]);
                            i = j;
                            break;
                        }
                    }
//                    else if ((arrIndex[j] - arrIndex[j - 1]) > 25) {//间断大于25帧，断开
//                        if (i >= 3) {//在非连续帧的开始前多添几帧，使音频不突兀
//                            timeInfo.put(arrIndex[i]-2, arrIndex[j - 1]);
//                        } else {
//                            timeInfo.put(arrIndex[i], arrIndex[j - 1]);
//                        }
//                        i = j;
//                        break;
//                    }
                    else if (arrIndex[j] + 1 != arrIndex[j]) {
                        if ((arrIndex[j] - arrIndex[j - 1]) > 30) {//间断大于25帧，断开
                            if ((j - 1) == i) {//起始位置就是间断
                                i = j;
                                break;
                            }
                            if ((arrIndex[j - 1] - arrIndex[i]) <= 10) {
                                i = j;
                                break;
                            }
                            timeInfo.put(arrIndex[i], arrIndex[j - 1]);
                            i = j;
                            break;
                        } else if ((arrIndex[j] - arrIndex[i]) >= 1400) {
                            timeInfo.put(arrIndex[i], arrIndex[j - 1]);
                            i = j;
                            break;
                        }
                    } else if (Objects.equals(j, arrIndex.length - 1)) {
//                        if (i != 0) {
//                            timeInfo.put(arrIndex[i - 2], arrIndex[j]);
//                        } else {
//                            timeInfo.put(arrIndex[i], arrIndex[j]);
//                        }
                        timeInfo.put(arrIndex[i], arrIndex[j]);
                        i = j;
                        break;
                    }
                }

            }

            Iterator<Integer> iterator1 = timeInfo.keySet().iterator();
            int con = 0;
            while (iterator1.hasNext()) {
                String outputpath = "D:\\pro\\videoToWav\\test\\test3\\" + con++ + ".wav";

                int key = iterator1.next();
                int val = timeInfo.get(key);

                long ke = key * 32;
                long va = val * 32;
                //截取音频文件
                clipAudio(input, outputpath, ke, va - ke);
            }
//            String outputpathTest = "D:\\pro\\videoToWav\\test\\test2\\testexce.wav";
//            clipAudio(input, outputpathTest, 240000, (8812 - 7414) * 32);
        } catch (TranslateException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static List<float[]> generateFrames(float[] data, int frameDurationMs, float sampleRate) {
        List<float[]> list = new ArrayList<>();
        int offset = 0;
        int n = (int) (sampleRate * (frameDurationMs / 1000.0));
        int length = data.length;
        while (offset + n < length) {
            float[] frame = Arrays.copyOfRange(data, offset, offset + n);
            offset += n;
            list.add(frame);
        }
        return list;
    }

    public static void clipAudio(String inputPath, String outputPath, long startMillis, long durationMillis) throws Exception {
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(inputPath));
        AudioFormat format = inputStream.getFormat();

        // 计算起始位置和截取长度
        float sampleRate = format.getSampleRate();
        float durationSeconds = durationMillis / 1000f;
        long startFrame = (long) (startMillis / 1000f * sampleRate);
        long lengthFrame = (long) (durationSeconds * sampleRate);

        // 创建一个子流，表示要截取的部分
        inputStream.skip(startFrame * format.getFrameSize());
        AudioInputStream clipStream = new AudioInputStream(inputStream, format, lengthFrame);
//        clipStream.skip(startFrame);

        // 写入输出文件
        AudioSystem.write(clipStream, AudioFileFormat.Type.WAVE, new File(outputPath));

        // 关闭流
        clipStream.close();
        inputStream.close();
    }

}
