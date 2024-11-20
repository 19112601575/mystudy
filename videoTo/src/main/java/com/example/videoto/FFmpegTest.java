package com.example.videoto;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FFmpegTest {
    private static Logger logger = LoggerFactory.getLogger(FFmpegTest.class);
    public static void main(String[] args) throws FrameGrabber.Exception, FFmpegFrameRecorder.Exception {
        String inputPath = "D:\\pro\\videoToWav\\wavcut\\voice\\32.wav";
        String outPath = "D:\\pro\\videoToWav\\wavcut\\my\\a.wav";

        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputPath);

        grabber.start();

        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outPath, grabber.getAudioChannels());

        recorder.start();

        Frame frame;

        while ((frame = grabber.grabFrame()) != null){
            recorder.record(frame);
            logger.info(String.valueOf(recorder.getGopSize()));
        }

        grabber.stop();
        grabber.release();
        recorder.stop();
        recorder.release();
    }
}
