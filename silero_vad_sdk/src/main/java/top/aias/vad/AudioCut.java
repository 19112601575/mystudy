package top.aias.vad;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class AudioCut {
    public static void cutAudio(String inputPath, double strartTime, double duration, String outPath) throws UnsupportedAudioFileException, IOException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(inputPath));

        double sampleRate = audioInputStream.getFormat().getSampleRate();
        long startFrame = (long) (strartTime * sampleRate);
        long framesLen = (long) (duration * sampleRate);
        AudioInputStream stream = new AudioInputStream(audioInputStream, audioInputStream.getFormat(), framesLen);

        AudioSystem.write(stream, AudioFileFormat.Type.WAVE, new File(outPath));
    }


    public static void main(String[] args) {
        String inputFilePath = "D:\\pro\\videoToWav\\video.wav";
        double startTime = 360.0; // seconds
        double duration = 60.0; // seconds
        String outputFilePath = "D:\\pro\\videoToWav\\output.wav";

        try {
            AudioCut.cutAudio(inputFilePath, startTime, duration, outputFilePath);
            System.out.println("Audio file successfully cut!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
