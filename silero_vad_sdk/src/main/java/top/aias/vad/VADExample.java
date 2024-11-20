package top.aias.vad;

import ai.djl.Device; // 用于指定模型运行在CPU还是GPU上
import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor; // 用于执行模型预测
import ai.djl.modality.audio.Audio; // 用于处理音频数据
import ai.djl.modality.audio.AudioFactory; // 用于创建Audio对象
import ai.djl.ndarray.NDArray; // 用于表示和操作数据
import ai.djl.ndarray.NDList; // 用于封装NDArray列表
import ai.djl.ndarray.NDManager; // 用于创建NDArray和执行操作
import ai.djl.ndarray.types.DataType; // 用于指定数据类型
import ai.djl.ndarray.types.Shape; // 用于指定数组形状
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel; // 用于加载模型
import ai.djl.translate.TranslateException;
import org.bytedeco.ffmpeg.global.avutil; // FFmpeg全局变量
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.slf4j.Logger; // 用于日志记录
import org.slf4j.LoggerFactory; // 用于创建Logger实例
import top.aias.vad.utils.SileroVAD; // 用于语音活动检测

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path; // 用于文件路径操作
import java.nio.file.Paths; // 用于创建Path对象
import java.util.ArrayList; // 用于创建动态数组
import java.util.Arrays; // 用于数组操作
import java.util.List; // 用于列表操作

/**
 * 语音活动检测(Voice Activity Detection,VAD)示例类
 *
 * @author Calvin
 * @email 179209347@qq.com
 * @website www.aias.top
 */
public class VADExample {
    private static final Logger logger = LoggerFactory.getLogger(VADExample.class); // 创建Logger实例

//    public static void main(String[] args) throws Exception {
//        // 获取音频文件路径
//        Path path = Paths.get("D:\\pro\\videoToWav\\wavcut\\2.wav");
//
//
//        // 创建Audio对象，设置音频参数并从文件加载音频数据
//        Audio audio =
//                AudioFactory.newInstance()
//                        .setChannels(1) // 设置单声道
//                        .setSampleRate(16000) // 设置采样率为16000Hz
//                        .setSampleFormat(avutil.AV_SAMPLE_FMT_S16) // 设置样本格式为16位有符号整数
//                        .fromFile(path); // 从文件加载音频数据
//
//        // 根据采样率和帧持续时间生成音频帧列表
//        List<float[]> frames = generateFrames(audio.getData(), 32, 16000);
//
//        // 初始化SileroVAD对象
//        SileroVAD vad = new SileroVAD();
//
//        // 加载模型并创建预测器
//        try (ZooModel<NDList, NDList> model = vad.criteria().loadModel(); // 加载模型
//             Predictor<NDList, NDList> predictor = model.newPredictor(); // 创建预测器
//             NDManager manager = NDManager.newBaseManager(Device.cpu(), "PyTorch")) { // 创建NDManager实例
//
//            // 设置测试阈值
//            float test_threshold = 0.5f;
//
//            // 初始化用于存储中间结果的NDArray
//            NDArray h_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);
//            NDArray c_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);
//
//            // 遍历所有音频帧
//            for (float[] frame : frames) {
//                // 将音频帧数据转换为NDArray，并重塑为模型所需的形状
//                NDArray audioFeature = manager.create(frame).reshape(1, frame.length).toType(DataType.FLOAT32, true);
//                // 创建包含采样率的NDArray
//                NDArray sampling_rate = manager.create(new int[]{16000}).toType(DataType.INT64, true);
//                // 创建NDList，包含音频特征和采样率
//                NDList list = new NDList(audioFeature, sampling_rate, h_ort, c_ort);
//
//                // 使用预测器进行预测
//                NDList result = predictor.predict(list);
//
//                // 获取预测结果
//                NDArray output = result.get(0);
//                // 获取分数
//                float score = output.toFloatArray()[0];
//                // 如果分数大于等于阈值，记录信息
////                logger.info(String.valueOf(score));
//                if (score >= test_threshold) {
//                    logger.info("score: " + score);
//                }
//
//                // 更新中间结果
//                h_ort = result.get(1);
//                c_ort = result.get(2);
//            }
//        }
//    }

    /**
     * 生成音频帧的方法
     *
     * @param data 音频数据数组
     * @param frameDurationMs 帧持续时间（毫秒）
     * @param sampleRate 采样率
     * @return 包含音频帧的列表
     */
    public static List<float[]> generateFrames(float[] data, int frameDurationMs, float sampleRate) {
        List<float[]> list = new ArrayList<>(); // 创建动态数组用于存储帧
        int offset = 0; // 初始化偏移量
        int n = (int) (sampleRate * (frameDurationMs / 1000.0)); // 计算每个帧的样本数
        int length = data.length; // 获取音频数据长度
        // 循环直到处理完所有数据
        while (offset + n < length) {
            float[] frame = Arrays.copyOfRange(data, offset, offset + n); // 复制当前帧到新数组
            offset += n; // 更新偏移量
            list.add(frame); // 将帧添加到列表中
        }
        return list; // 返回包含所有帧的列表
    }

    public boolean vaD(File file) throws IOException, ModelNotFoundException, MalformedModelException, TranslateException, UnsupportedAudioFileException {
//            String outPath = "D:\\pro\\videoToWav\\cutdown\\";
            // 获取音频文件路径
            Path path = Paths.get(file.getPath());
            // 创建Audio对象，设置音频参数并从文件加载音频数据
            Audio audio =
                    AudioFactory.newInstance()
                            .setChannels(1) // 设置单声道
                            .setSampleRate(16000) // 设置采样率为16000Hz
                            .setSampleFormat(avutil.AV_SAMPLE_FMT_S16) // 设置样本格式为16位有符号整数
                            .fromFile(path); // 从文件加载音频数据

            // 根据采样率和帧持续时间生成音频帧列表
            List<float[]> frames = generateFrames(audio.getData(), 32, 16000);

            // 初始化SileroVAD对象
            SileroVAD vad = new SileroVAD();

            // 加载模型并创建预测器
            try (ZooModel<NDList, NDList> model = vad.criteria().loadModel(); // 加载模型
                 Predictor<NDList, NDList> predictor = model.newPredictor(); // 创建预测器
                 NDManager manager = NDManager.newBaseManager(Device.cpu(), "PyTorch")) { // 创建NDManager实例

                // 设置测试阈值
                float test_threshold = 0.5f;

                // 初始化用于存储中间结果的NDArray
                NDArray h_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);
                NDArray c_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);


//                FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outPath, 1, 1);
                // 遍历所有音频帧
                for (float[] frame : frames) {
                    // 将音频帧数据转换为NDArray，并重塑为模型所需的形状
                    NDArray audioFeature = manager.create(frame).reshape(1, frame.length).toType(DataType.FLOAT32, true);
                    // 创建包含采样率的NDArray
                    NDArray sampling_rate = manager.create(new int[]{16000}).toType(DataType.INT64, true);
                    // 创建NDList，包含音频特征和采样率
                    NDList list = new NDList(audioFeature, sampling_rate, h_ort, c_ort);

                    // 使用预测器进行预测
                    NDList result = predictor.predict(list);

                    // 获取预测结果
                    NDArray output = result.get(0);
                    // 获取分数
                    float score = output.toFloatArray()[0];


                    // 如果分数大于等于阈值，记录信息
//                logger.info(String.valueOf(score));
                    if (score >= test_threshold) {
                        logger.info("score: " + score);

                        return true;
                    }

                    // 更新中间结果
                    h_ort = result.get(1);
                    c_ort = result.get(2);

                }
                return false;
            }

        }

}
