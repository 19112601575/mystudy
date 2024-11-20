package top.aias.vad;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VIdeoPic {
    public static void main(String[] args) {

        String inputPath = "D:\\pro\\videoToWav\\20240805135121-meeting_01-视频-1.mp4";
        String outPath ="D:\\pro\\videoToWav\\\\image\\1.jpg";

        videoImage(inputPath,outPath,20);
    }

    public static void videoImage(String inputPath,String outPath,int secs){
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputPath);
        try {
            grabber.start();

            Frame f =null;

            Double v = Double.parseDouble(String.valueOf(secs));
            long targetFrame  = (long) (v *1000*1000);

            grabber.setTimestamp(targetFrame);

            f =grabber.grabImage();
            if (f !=null){
                int width = f.imageWidth;
                int height = f.imageHeight;
                Java2DFrameConverter converter = new Java2DFrameConverter();
                BufferedImage image = converter.getBufferedImage(f);

                BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
                bufferedImage.getGraphics().drawImage(image.getScaledInstance(width,height, Image.SCALE_SMOOTH),0,0,null);

                ImageIO.write(bufferedImage,"jpg",new File(outPath));
            }
            grabber.stop();
        } catch (FFmpegFrameGrabber.Exception e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
