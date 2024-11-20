package top.aias.vad;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;

public class Vadex1 {
    public  void clipAudio(String inputPath, String outputPath, long startMillis, long durationMillis) throws Exception {
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

    public static void main(String[] args) {
        Vadex1 vadex = new Vadex1();
        try {
            vadex.clipAudio("D:\\pro\\videoToWav\\video.wav", "D:\\pro\\videoToWav\\output.wav", 420000, 600000); // 从1秒开始截取3秒长的音频
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
