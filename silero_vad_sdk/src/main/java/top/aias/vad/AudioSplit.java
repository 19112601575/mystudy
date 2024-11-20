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

public class AudioSplit {
    public static void main(String[] args) throws Exception {
        String inputPath = "D:\\pro\\videoToWav\\test\\111.wav";

        splitAudio(inputPath);
    }

    private static final Logger logger = LoggerFactory.getLogger(SplitAudio.class);


    public static List<Map<String, Object>> splitAudio(String inputPath) throws Exception {


        Path path = Paths.get(inputPath);
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

            float threshold = 0.5f;
// 创建两个NDArray，用于存储输入和输出数据，初始化为全零
            NDArray hOrt = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);
            NDArray cOrt = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);

            int index = 0;
            Map<Integer, Float> indexScore = new LinkedHashMap<>();
            Map<Integer, Integer> timeInfo = new LinkedHashMap<>();
            for (float[] frame : frames) {
                NDArray audioFeature = manager.create(frame).reshape(1, frame.length).toType(DataType.FLOAT32, true);
                NDArray samplingRate = manager.create(new int[]{16000}).toType(DataType.INT64, true);
                NDList list = new NDList(audioFeature, samplingRate, hOrt, cOrt);

                NDList result = predictor.predict(list);

                NDArray output = result.get(0);
                float score = output.toFloatArray()[0];

                if (score >= threshold) {
                    indexScore.put(index, score);
                }

                index++;
                hOrt = result.get(1);
                cOrt = result.get(2);
            }

            //遍历map集合，获取index
            Iterator<Integer> iterator = indexScore.keySet().iterator();
            int[] arrIndex = new int[indexScore.size()];//将index存入数组
            int x = 0;
            while (iterator.hasNext()) {
                Integer key = iterator.next();
                arrIndex[x] = key;
                x++;
            }

            //返回开始时间、结束时间
            List<Map<String, Object>> listTimePath = new ArrayList<>();
            ArrayList<String> listTime = new ArrayList<>();
            //获得音频流
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(inputPath));
            //存储索引范围
            int min = 1400;

            for (int i = 0; i < arrIndex.length - 1; i++) {
                for (int j = i + 1; j < arrIndex.length; j++) {
                    if ((arrIndex[j] + 1) == arrIndex[j]) {
                        if ((arrIndex[j] - arrIndex[i]) >= min) {
                            //连续帧，断开
                            timeInfo.put(arrIndex[i], arrIndex[j]);
                            //存时间
                            listTime.add(arrIndex[i] * 32 + "-" + arrIndex[j] * 32);
                            i = j;
                            break;
                        }

                    } else if (arrIndex[j] + 1 != arrIndex[j]) {
                        if ((arrIndex[j] - arrIndex[j - 1]) >= 30) {
                            if ((j - 1) == i) {
                                i = j;
                                break;
                            }
                            if ((arrIndex[j - 1] - arrIndex[i]) <= 10) {
                                i = j;
                                break;
                            }
                            timeInfo.put(arrIndex[i], arrIndex[j - 1]);
                            listTime.add(arrIndex[i] * 32 + "-" + arrIndex[j - 1] * 32);
                            i = j;
                            break;
                        } else if ((arrIndex[j] - arrIndex[i]) >= min) {
                            timeInfo.put(arrIndex[i], arrIndex[j - 1]);
                            listTime.add(arrIndex[i] * 32 + "-" + arrIndex[j - 1] * 32);
                            i = j;
                            break;
                        }
                    } else if (Objects.equals(j, arrIndex.length - 1)) {//结尾
                        timeInfo.put(arrIndex[i], arrIndex[j]);
                        listTime.add(arrIndex[i] * 32 + "-" + arrIndex[j] * 32);
                        i = j;
                        break;
                    }
                }
            }

            Iterator<Integer> iterator1 = timeInfo.keySet().iterator();


            int count = 0;
            while (iterator1.hasNext()) {
//                String outPath = "D:\\pro\\videoToWav\\5-15" + count++ + ".wav";
                File tempFile = new File("D:\\pro\\videoToWav\\test\\test3\\" + count++ + ".wav");
//                    String timeInfoAll = listTime.get(count);
//                    String startTime = timeInfoAll.substring(0, timeInfoAll.indexOf("-"));
//                    String endTime = timeInfoAll.substring(startTime.length() + 1, timeInfoAll.length());
////                mapTimePath.put(listTime.get(count),tempFile.getPath());
//                    Map<String, Object> mapTimePath = new LinkedHashMap<>();
//                    mapTimePath.put("startTime",startTime);
//                    mapTimePath.put("endTime",endTime);
//                    mapTimePath.put("path",tempFile.getPath());
//                    listTimePath.add(mapTimePath);
                int key = iterator1.next();
                int val = timeInfo.get(key);

                long ke = key * 32;
                long va = val * 32;
                //截取音频文件
                clipAudio(inputPath, tempFile.getPath(), ke, va - ke);
            }
            return listTimePath;
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



