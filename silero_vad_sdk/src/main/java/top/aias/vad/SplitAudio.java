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

import top.aias.*;
import top.aias.vad.utils.SileroVAD;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class SplitAudio {
    private static final Logger logger = LoggerFactory.getLogger(SplitAudio.class);

    public List<Map<String, Object>> splitAudio(String inputPath) throws Exception {


        Path path = Paths.get(inputPath);
        Audio audio =
                AudioFactory.newInstance()
                        .setChannels(1)
                        .setSampleRate(16000)
                        .setSampleFormat(avutil.AV_SAMPLE_FMT_S16)
                        .fromFile(path);

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

            int index = 0;
            Map<Integer, Float> mapS = new LinkedHashMap<>();
            Map<Integer, Integer> mapC = new LinkedHashMap<>();
            for (float[] frame : frames) {
                NDArray audioFeature = manager.create(frame).reshape(1, frame.length).toType(DataType.FLOAT32, true);
                NDArray sampling_rate = manager.create(new int[]{16000}).toType(DataType.INT64, true);
                NDList list = new NDList(audioFeature, sampling_rate, h_ort, c_ort);

                NDList result = predictor.predict(list);

                NDArray output = result.get(0);
                float score = output.toFloatArray()[0];

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

            //返回开始时间、结束时间
            List<Map<String, Object>> listTimePath = new ArrayList<>();
            Map<String, Object> mapTimePath = new LinkedHashMap<>();
            ArrayList<String> listTime = new ArrayList<>();
            //存储索引范围
            int min = 1400;
            for (int i = 0; i < arrIndex.length - 1; i++) {
                for (int j = i + 1; j < arrIndex.length; j++) {
                    if (arrIndex[j - 1] + 1 == arrIndex[j] && arrIndex[j] - arrIndex[i] >= min) {//连续帧，断开
//                        if (i==0){
//
//                        }
                        mapC.put(arrIndex[i], arrIndex[j]);
                        //存时间
                        listTime.add(arrIndex[i] * 32 + "-" + arrIndex[j] * 32);
                        i = j;
                        break;
                    } else if ((arrIndex[j] - arrIndex[j - 1]) > 15) {//间断大于15帧，分段
                        mapC.put(arrIndex[i], arrIndex[j - 1]);
                        listTime.add(arrIndex[i] * 32 + "-" + arrIndex[j-1] * 32);
                        i = j;
                        break;
                    } else if (Objects.equals(j, arrIndex.length - 1)) {//结尾
                        mapC.put(arrIndex[i], arrIndex[j]);
                        listTime.add(arrIndex[i] * 32 + "-" + arrIndex[j] * 32);
                        i = j;
                        break;
                    }
                }

            }

            Iterator<Integer> iterator1 = mapC.keySet().iterator();


            int count = 0;
            while (iterator1.hasNext()) {
                String outPath = "D:\\pro\\videoToWav\\5-15" + count++ + ".wav";


                mapTimePath.put(listTime.get(count),outPath);
                int key = iterator1.next();
                int val = mapC.get(key);

                long ke = key * 32;
                long va = val * 32;
                //截取音频文件
                clipAudio(inputPath, outPath, ke, va - ke);
            }
            listTimePath.add(mapTimePath);
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
        inputStream.skip(startFrame * 4);
        AudioInputStream clipStream = new AudioInputStream(inputStream, format, lengthFrame);
//        clipStream.skip(startFrame);

        // 写入输出文件
        AudioSystem.write(clipStream, AudioFileFormat.Type.WAVE, new File(outputPath));

        // 关闭流
        clipStream.close();
        inputStream.close();
    }

}

