package top.aias.vad;

import ai.djl.Device;
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.DataType;
import ai.djl.ndarray.types.Shape;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.aias.vad.utils.SileroVAD;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyVad {
    private static Logger logger = LoggerFactory.getLogger(MyVad.class);
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException, TranslateException, ModelNotFoundException, MalformedModelException {
        File audioFile = new File("audio.wav");
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
        AudioFormat format = audioStream.getFormat();
        AudioFormat newFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                format.getSampleRate(),
                16,
                format.getChannels(),
                format.getChannels() * 2,
                format.getSampleRate(),
                false
        );
        AudioInputStream newStream = AudioSystem.getAudioInputStream(newFormat, audioStream);
        byte[] audioData = new byte[newStream.available()];
        newStream.read(audioData);

        int frameSize = 1024; // 帧大小
        int frameOverlap = 512; // 帧重叠大小
        float energyThreshold = 0.5f; // 能量阈值

        List<byte[]> voiceFrames = new ArrayList<>(); // 保存语音帧的列表

        int offset = 0;
        while (offset + frameSize <= audioData.length) {
            byte[] frame = Arrays.copyOfRange(audioData, offset, offset + frameSize);

            // 计算帧的能量
            float energy = calculateEnergy(frame);

            // 判断能量是否超过阈值
            if (energy > energyThreshold) {
                voiceFrames.add(frame);
            }

            offset += frameSize - frameOverlap;
        }
    }

    static float calculateEnergy(byte[] frames) throws ModelNotFoundException, MalformedModelException, IOException, TranslateException {

//        SileroVAD vad = new SileroVAD();
//        ZooModel<NDList, NDList> model = vad.criteria().loadModel();
//        Predictor<NDList, NDList> predictor = model.newPredictor();
//        NDManager manager = NDManager.newBaseManager(Device.cpu(), "PyTorch");
//        {
//
//            NDArray h_ort = manager.zeros(new ai.djl.ndarray.types.Shape(2, 1, 64), DataType.FLOAT32);
//            NDArray c_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);
//
//            // 将音频帧数据转换为NDArray，并重塑为模型所需的形状
//            NDArray audioFeature = manager.create(frames).reshape(1, frames.length).toType(DataType.FLOAT32, true);
//            // 创建包含采样率的NDArray
//            NDArray sampling_rate = manager.create(new int[]{16000}).toType(DataType.INT64, true);
//            // 创建NDList，包含音频特征和采样率
//            NDList list = new NDList(audioFeature, sampling_rate, h_ort, c_ort);
//
//            // 使用预测器进行预测
//            NDList result = predictor.predict(list);
//
//            // 获取预测结果
//            NDArray output = result.get(0);
//            // 获取分数
//            float score = output.toFloatArray()[0];
//
//            return score;
//            // 更新中间结果
//            h_ort = result.get(1);
//            c_ort = result.get(2);
//
//        }
//
        return 1;
    }
}
