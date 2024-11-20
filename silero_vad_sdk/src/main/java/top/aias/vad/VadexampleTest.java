package top.aias.vad;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.translate.TranslateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class VadexampleTest {
    private static Logger logger = LoggerFactory.getLogger(VadexampleTest.class);
    public static void main(String[] args) throws UnsupportedAudioFileException, TranslateException, ModelNotFoundException, MalformedModelException, IOException {
        File file = new File("D:\\pro\\videoToWav\\wavcut\\voice\\32.wav");
        VADExample vadExample = new VADExample();
        boolean b = vadExample.vaD(file);
        logger.info(String.valueOf(b));
    }
}
