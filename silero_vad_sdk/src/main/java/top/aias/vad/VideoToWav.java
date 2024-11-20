package top.aias.vad;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import javax.sound.sampled.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class VideoToWav {
    // 定义视频文件和音频文件的路径
    static String videoPath = "D:\\\\pro\\\\videoToWav\\20240805135121-meeting_01-视频-1.mp4";
    static String audioPath = "D:\\\\pro\\\\videoToWav\\test\\111.wav";

    // 定义输出路径
    static String outPath = "D:\\\\pro\\\\videoToWav\\\\wavcut\\\\all\\";
    static String outPath1 = "D:\\pro\\videoToWav\\wavcut\\voice\\";
    static String outPath2 = "D:\\pro\\videoToWav\\wavcut\\silence\\";

    public static void main(String[] args) throws UnsupportedAudioFileException, IOException, TranslateException, ModelNotFoundException, MalformedModelException {
        long start = System.currentTimeMillis();
//        File fileMp3 = File.createTempFile("111",".wav");

        // 将视频转换为音频
        videoToAudio(videoPath, audioPath);
        long end = System.currentTimeMillis();
        long mills = end - start; // 计算转换时间
        double seconds = mills / 1000.0;
        System.out.println(seconds + "秒");
        // 将音频文件切分为多个小文件
//        wavSplitter(audioPath, outPath);
    }

    /**
     * 将视频文件转换为音频文件
     *
     * @param videoPath 视频文件路径

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
    public static void wavSplitter(String inputPath, String outPath) throws IOException, UnsupportedAudioFileException, TranslateException, ModelNotFoundException, MalformedModelException {
        File file = new File(inputPath);
        // 获取音频输入流
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

        // 获取音频格式
        AudioFormat format = audioInputStream.getFormat();
        int frameSize = format.getFrameSize();//采样率
        float frameRate = format.getFrameRate();//比特率

        int intervalInSeconds = 10; // 切分间隔，单位为秒
        byte[] buffer = new byte[(int) (frameRate * intervalInSeconds * frameSize)];
        int bytesRead = 0;
        int count = 0;

        VADExample vadExample = new VADExample();
//        new VADExample();
        // 读取音频数据并切分
        while ((bytesRead = audioInputStream.read(buffer)) != -1) {
            File outputFile = new File(outPath +   count + ".wav");
            File outputFile1 = new File(outPath1 +   count + ".wav");
            File outputFile2 = new File(outPath2 +   count + ".wav");
            // 将切分后的音频数据写入文件
             AudioInputStream outputAudioInputStream = new AudioInputStream(new ByteArrayInputStream(buffer), format, bytesRead);
             AudioSystem.write(outputAudioInputStream, AudioFileFormat.Type.WAVE, outputFile);

            if (vadExample.vaD(outputFile)){
                AudioInputStream outputAudioInputStream1 = new AudioInputStream(new ByteArrayInputStream(buffer), format, bytesRead);
                AudioSystem.write(outputAudioInputStream1, AudioFileFormat.Type.WAVE, outputFile1);
            }else {
                AudioInputStream outputAudioInputStream2 = new AudioInputStream(new ByteArrayInputStream(buffer), format, bytesRead);
                AudioSystem.write(outputAudioInputStream2, AudioFileFormat.Type.WAVE, outputFile2);
            }
            count++;
        }
    }



}
