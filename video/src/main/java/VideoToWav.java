
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

public class VideoToWav {
    String videoPath = "D:\\pro\\videoTomp3\\video.mp4";
    String audioPath = "D:\\pro\\videoTomp3\\";
    public static void main(String[] args) {
        videoToAudio("D:\\pro\\videoTomp3\\video.mp4","D:\\pro\\videoTomp3\\");
    }
    /**
     * 视频文件转音频文件
     * @param videoPath
     * @param audioPath
     * return true or false
     */

    public static boolean videoToAudio(String videoPath, String audioPath){
        File fileMp4 = new File(videoPath);
        File fileMp3 = new File(audioPath);

        Logger Log = LoggerFactory.getLogger(VideoToWav.class);

        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(128000);
        audio.setChannels(2);
        audio.setSamplingRate(44100);

        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setOutputFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        MultimediaObject mediaObject = new MultimediaObject(fileMp4);
        try{
            encoder.encode(mediaObject,fileMp3,attrs);
            Log.info("File MP4 convertito MP3");
            return true;
        }catch (Exception e){
            Log.error("File non convertito");
            Log.error(e.getMessage());
            return false;
        }
    }

}
