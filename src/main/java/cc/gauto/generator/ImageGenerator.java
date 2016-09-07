package cc.gauto.generator;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-09-06 22:14
 */
public class ImageGenerator {

    private static ImageGenerator imageGenerator = null;

    //private int imageWidth;
    //private int imageHeight;
    //private BufferedImage bufferedImage;

    public static ImageGenerator getInstance() {
        if (imageGenerator == null) {
            imageGenerator = new ImageGenerator();
        }
        return imageGenerator;
    }

    private ImageGenerator(){
    }

    //根据数据库文件生成相关分辨率的图片
    public BufferedImage generateImage(String dbUrl, int imageWidth, int imageHeight) {
        BufferedImage bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, 1920, 1080);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.RED);
        g2.drawLine(100, 100, 700, 700);
//        try {
//            Image img = ImageIO.read(new File("pic.jpg"));
//            g2.drawImage(img, 10, 10, 100, 100, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return bufferedImage;
    }

}
