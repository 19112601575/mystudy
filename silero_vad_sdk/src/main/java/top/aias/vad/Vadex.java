package top.aias.vad;

import java.io.File;

import javax.sound.sampled.*;
import java.io.*;

public class Vadex {
    public  void clipAudio(String inputPath, String outputPath, long startMillis, long durationMillis) throws Exception {
        AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(inputPath));
        AudioFormat format = inputStream.getFormat();

        // 计算起始位置和截取长度
        float sampleRate = format.getSampleRate();
        float durationSeconds = durationMillis / 1000f;
        float startSeconds = startMillis / 1000f;
        long startFrame = (long) (startSeconds * sampleRate);
        long lengthFrame = (long) ((durationSeconds) * sampleRate);

        // 创建一个子流，表示要截取的部分
        AudioInputStream clipStream = new AudioInputStream(inputStream, format, startFrame + lengthFrame);
        long skipBytes = clipStream.skip(startFrame);

        // 写入输出文件
        AudioSystem.write(clipStream, AudioFileFormat.Type.WAVE, new File(outputPath));

        // 关闭流
        clipStream.close();
        inputStream.close();
    }

    public static void main(String[] args) {
        Vadex vadex = new Vadex();
        try {
            vadex.clipAudio("D:\\pro\\videoToWav\\video.wav", "D:\\pro\\videoToWav\\output.wav", 300000, 600000); // 从1秒开始截取3秒长的音频
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}