package top.aias.vad.voiceprint;

import ai.djl.Device;
import ai.djl.inference.Predictor;
import ai.djl.ndarray.NDManager;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * 声纹识别
 * Voiceprint recognition
 *
 * <p>https://github.com/yeyupiaoling/VoiceprintRecognition-PaddlePaddle
 *
 * @author calvin
 * @mail 179209347@qq.com
 * @website www.aias.top
 */
public final class VoiceprintExample {
  private static final Logger logger = LoggerFactory.getLogger(VoiceprintExample.class);

  private VoiceprintExample() {}

  public static boolean voicePrint() throws Exception {

    NDManager manager = NDManager.newBaseManager(Device.cpu());

    String audioFilePath1 = "D:\\pro\\videoToWav\\vad\\0.wav";
    String audioFilePath2 = "D:\\pro\\videoToWav\\vad\\1.wav";
    String audioFilePath3 = "D:\\pro\\videoToWav\\vad\\2.wav";

    float[][] mag1 = JLibrasaEx.magnitude(manager, audioFilePath1);
    float[][] mag2 = JLibrasaEx.magnitude(manager, audioFilePath2);
    float[][] mag3 = JLibrasaEx.magnitude(manager, audioFilePath3);

    Voiceprint voiceprint = new Voiceprint();
    Criteria<float[][], float[]> criteria = voiceprint.criteria();

    try (ZooModel<float[][], float[]> model = criteria.loadModel();
        Predictor<float[][], float[]> predictor = model.newPredictor()) {

      logger.info("input audio: {}", "src/test/resources/a_1.wav");
      logger.info("input audio: {}", "src/test/resources/a_2.wav");
      logger.info("input audio: {}", "src/test/resources/b_1.wav");

      float[] feature1 = predictor.predict(mag1);
      logger.info("a_1.wav feature: " + Arrays.toString(feature1));
      float[] feature2 = predictor.predict(mag2);
      logger.info("a_2.wav feature: " + Arrays.toString(feature2));
      float[] feature3 = predictor.predict(mag3);
      logger.info("b_1.wav feature: " + Arrays.toString(feature3));

      logger.info(
          "a_1.wav,a_2.wav Similarity： " + Float.toString(FeatureUtils.calculSimilar(feature1, feature2)));
      logger.info(
          "a_1.wav,b_1.wav Similarity： " + Float.toString(FeatureUtils.calculSimilar(feature1, feature3)));
    }
  }
}
