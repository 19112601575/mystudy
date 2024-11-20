package com.example.videoto;

import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioTest {
//    Logger logger = LoggerFactory.getLogger(AudioTest.class);
    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {

        File file = new File("D:\\pro\\videoToWav\\wavcut\\voice\\32.wav");

        String outpath = "D:\\pro\\videoToWav\\wavcut\\mytest\\a.wav";
        // 获取音频输入流
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

        // 获取音频格式
        AudioFormat format = audioInputStream.getFormat();
        int frameSize = format.getFrameSize();//采样率
        float frameRate = format.getFrameRate();//比特率

        avutil.av_log_set_level(avutil.AV_LOG_ERROR);
        FFmpegLogCallback.set();
//        logger.info(format.toString());

        byte[] bytes = new byte[1024 * 2];

        // 利用数据行类型和音频格式创建数据行对象
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

        SourceDataLine auline = null;
        // 利用音频系统类获取指定对象中匹配的行对象
        auline = (SourceDataLine) AudioSystem.getLine(info);
        auline.open(format); // 用指定格式打开数据行
        auline.start();
        int byteCount = 0;
        while (byteCount != -1) {
            byteCount = audioInputStream.read(bytes, 0, bytes.length); // 从数据流中读出128KB数据
            if (byteCount >= 0) {
                auline.write(bytes, 0, byteCount); // 将数据写入数据行
            }
        }

    }
}
