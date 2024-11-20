package com.example.videoto;

import be.tarsos.dsp.SilenceDetector;
import be.tarsos.dsp.io.TarsosDSPAudioFloatConverter;
import be.tarsos.dsp.io.TarsosDSPAudioFormat;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import javax.sound.sampled.*;
import java.io.*;

import static org.bytedeco.ffmpeg.global.avutil.av_log_set_callback;


public class VideoToWav {
    private static Logger logger = LoggerFactory.getLogger(VideoToWav.class);

    // 定义视频文件和音频文件的路径
    static String videoPath = "D:\\\\pro\\\\videoToWav\\\\video.mp4";
    static String audioPath = "D:\\\\pro\\\\videoToWav\\\\audio.wav";

    // 定义输出路径
    static String outPath = "D:\\pro\\videoToWav\\wavcut\\mytest\\";

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        long start = System.currentTimeMillis();
        // 将视频转换为音频
        videoToAudio(videoPath, audioPath);
        long end = System.currentTimeMillis();
        long mills = end - start; // 计算转换时间
        double seconds = mills / 1000.0;
        System.out.println(seconds + "秒");
        // 将音频文件切分为多个小文件
        wavSplitter(audioPath, outPath);
    }

    /**
     * 将视频文件转换为音频文件
     *
     * @param videoPath 视频文件路径
     * @param audioPath 音频文件路径
     * @return 转换是否成功
     */
    public static boolean videoToAudio(String videoPath, String audioPath) {
        File fileMp4 = new File(videoPath);
        File fileMp3 = new File(audioPath);

        // 设置音频属性
        AudioAttributes audio = new AudioAttributes();
        audio.setBitRate(16000);
        audio.setCodec("pcm_s16le");
        audio.setChannels(1);
        audio.setSamplingRate(16000);

        // 设置编码属性
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("wav");
        attrs.setAudioAttributes(audio);

        // 创建编码器
        Encoder encoder = new Encoder();
        MultimediaObject mediaObject = new MultimediaObject(fileMp4);
        try {
            // 进行编码
            encoder.encode(mediaObject, fileMp3, attrs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 将WAV音频文件切分为多个小文件
     *
     * @param inputPath 输入的WAV文件路径
     * @param outPath   输出的文件夹路径
     * @throws IOException
     * @throws UnsupportedAudioFileException
     */
    public static void wavSplitter(String inputPath, String outPath) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File file = new File(inputPath);

        String outpath = outPath + "a.wav";
        // 获取音频输入流
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

        // 获取音频格式
        AudioFormat format = audioInputStream.getFormat();
        int frameSize = format.getFrameSize();//采样率
        float frameRate = format.getFrameRate();//比特率

        avutil.av_log_set_level(avutil.AV_LOG_ERROR);
        FFmpegLogCallback.set();
        logger.info(format.toString());

        byte[] bytes = new byte[1024 * 2];



//3.
//        try {
//            grabber.start();
//            logger.info(grabber.getAudioCodecName());
//            FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outpath, grabber.getAudioChannels());
//
//            recorder.setAudioCodec(grabber.getAudioCodec());
//            recorder.setFormat(grabber.getFormat());
//            recorder.setSampleRate(grabber.getSampleRate());
//            recorder.setAudioBitrate(grabber.getAudioBitrate());
//
//            recorder.start();
//            FFmpegLogCallback.set();
//            Frame frame;
//
//            while ((frame = grabber.grabFrame()) != null) {
//                recorder.record(frame);
//            }
//
//            grabber.stop();
//            grabber.release();
//            recorder.stop();
//            recorder.release();
//        }catch (FrameGrabber.Exception | FrameRecorder.Exception e){
//            e.printStackTrace();
//        }




//切分
//        int intervalInSeconds = 10; // 切分间隔，单位为秒
//        byte[] buffer = new byte[(int) (frameRate * intervalInSeconds * frameSize)];
//        int bytesRead = 0;
//        int count = 0;
//
//        // 读取音频数据并切分
//        while ((bytesRead = audioInputStream.read(buffer)) != -1) {
//            File outputFile = new File(outPath + 10 * count + "-" + (10 * count + 10) + ".wav");
//            AudioInputStream outputAudioInputStream = new AudioInputStream(new ByteArrayInputStream(buffer), format, bytesRead);
//            // 将切分后的音频数据写入文件
//            AudioSystem.write(outputAudioInputStream, AudioFileFormat.Type.WAVE, outputFile);
//            count++;
//        }

    }


}
