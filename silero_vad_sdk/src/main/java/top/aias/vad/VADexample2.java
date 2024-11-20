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
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.aias.vad.utils.SileroVAD;

import javax.sound.sampled.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VADexample2 {
    private static Logger logger = LoggerFactory.getLogger(VADexample2.class);
    private static int  coun = 0;
    private static final int SAMPLERATE = 16000;
    public static void main(String[] args) throws Exception {

//        Path path = Paths.get("src/test/resources/test.wav");
        String path = "D:\\pro\\videoToWav\\wavcut\\video.wav";
        Path path1 = Paths.get(path);
        Audio audio =
                AudioFactory.newInstance()
                        .setChannels(1)
                        .setSampleRate(SAMPLERATE)
                        .setSampleFormat(avutil.AV_SAMPLE_FMT_S16)
                        .fromFile(path1);


        // window_size_samples [512, 1024, 1536] for 16000 sampling_rate
        // window_size_samples = (frame_duration_ms / 10000) * sampling_rate
        // frame_duration_ms = window_size_samples / (sampling_rate / 1000)
        // frame_duration_ms: 512/16 = 32, 1024/16 = 64, 1536/16 = 96

        List<float[]> frames = generateFrames(audio.getData(), 100, 16000);

        SileroVAD vad = new SileroVAD();
        try (
                ZooModel<NDList, NDList> model = vad.criteria().loadModel();
                Predictor<NDList, NDList> predictor = model.newPredictor();
                NDManager manager = NDManager.newBaseManager(Device.cpu(), "PyTorch")) {

            float test_threshold = 0.5f;

            NDArray h_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);
            NDArray c_ort = manager.zeros(new Shape(2, 1, 64), DataType.FLOAT32);

            int count = 0;
            int temp = 0;
            String outpath = "D:\\pro\\videoToWav\\wavcut\\my\\" + coun + ".wav";
            top.aias.vad.utils.Path path2 = new top.aias.vad.utils.Path();
            File file = new File(path2.path());
            for (float[] frame : frames) {
                if (temp >= 700 && temp <= 800) {
                    temp = 0;
                    path2.setCount(count++);
                }


//                FileOutputStream fos = new FileOutputStream(outpath);

//                DataOutputStream dos = new DataOutputStream(fos);
                NDArray audioFeature = manager.create(frame).reshape(1, frame.length).toType(DataType.FLOAT32, true);
                NDArray sampling_rate = manager.create(new int[]{16000}).toType(DataType.INT64, true);
                NDList list = new NDList(audioFeature, sampling_rate, h_ort, c_ort);

                NDList result = predictor.predict(list);

                NDArray output = result.get(0);
                float score = output.toFloatArray()[0];

// 1.               ByteBuffer byteBuffer = ByteBuffer.allocate(frame.length * 4);
//                byte[] bytes = byteBuffer.array();
//                bytes = dataValueRollback(bytes);

//                int intervalInSeconds = 10; // 切分间隔，单位为秒
//                byte[] buffer = new byte[(int) (frameRate * intervalInSeconds * frameSize)];
                final byte[] byteBuffer = new byte[frame.length * 2];
                int bufferIndex = 0;
                for (int i = 0; i < byteBuffer.length; i++) {
                    final int x = (int) (frame[bufferIndex++] * 32767.0);
                    byteBuffer[i++] = (byte) x;
                    byteBuffer[i] = (byte) (x >>> 8);
                }
                // 将float数据转换为byte数据
//                for (int i = 0; i < frame.length; i++) {
//                    byteBuffer[2 * i] = (byte) (frame[i] < 0 ? (frame[i] * 127.5) : (frame[i] * 127.5 + 128));
//                    byteBuffer[2 * i + 1] = byteBuffer[2 * i];
//                }

                // 获取音频格式
//                File file = new File(path);
//                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//                AudioFormat format = audioInputStream.getFormat();
//                int frameSize = format.getFrameSize();//采样率
//                float frameRate = format.getFrameRate();//比特率

                final boolean bigEndian = false;
                final boolean signed = true;
                double sampleRate = 16000.0; //控制速度
                final int bits = 16;
                final int channels = 1;

                AudioFormat format = new AudioFormat((float) sampleRate, bits, channels, signed, bigEndian);
                ByteArrayInputStream bais = new ByteArrayInputStream(byteBuffer);


                int bytesRead = 0;
                if (score >= test_threshold) {
                    logger.info("score: " + score);
//                    while ((bytesRead = audioInputStream.read(bytes)) != -1) {
//                        // 将切分后的音频数据写入文件
//                        AudioInputStream outputAudioInputStream = new AudioInputStream(new ByteArrayInputStream(bytes), format, bytesRead);
//                        AudioSystem.write(outputAudioInputStream, AudioFileFormat.Type.WAVE, new File(outpath));
//                    }
                    AudioInputStream audioInputStream = new AudioInputStream(bais, format, frame.length);
                    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file);
                    audioInputStream.close();
                    temp++;

                }
//                if (score >= test_threshold) {
//                    logger.info("score: " + score);
//                    for (byte b1 : bytes){
//                        dos.write(bytes);
//                    }
//                    temp++;
//                }
                h_ort = result.get(1);
                c_ort = result.get(2);


            }


        }
    }

    //数值反传
    private static byte[] dataValueRollback(byte[] data) {
        ArrayList<Byte> al = new ArrayList<Byte>();
        for (int i = data.length - 1; i >= 0; i--) {
            al.add(data[i]);
        }

        byte[] buffer = new byte[al.size()];
        for (int i = 0; i <= buffer.length - 1; i++) {
            buffer[i] = al.get(i);
        }
        return buffer;
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
}
