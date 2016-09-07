package cc.gauto;

import cc.gauto.db.DBHelper;
import cc.gauto.db.entity.ExamEntity;
import cc.gauto.generator.ImageGenerator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-09-06 22:01
 */
public class Main {
    public static void main(String[] args) {
        String url = "db-data/bjhd/map-db-decrypted.sqlite";
        DBHelper dbHelper = DBHelper.getInstance(url);
        List<ExamEntity> examEntities = dbHelper.getExam();
        for (ExamEntity examEntity : examEntities) {
            System.out.println("id: " + examEntity.polygonId + "    name: " + examEntity.name);
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {

                //createAndShowGui();
                int screenWidth = 1920;
                int screenHeight = 1080;
                ImageGenerator imageGenerator = ImageGenerator.getInstance();
                BufferedImage bufferedImage = imageGenerator.generateImage(url, screenWidth, screenHeight);

                GraphPanel mainPanel = new GraphPanel();
                mainPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
                mainPanel.setShowImage(bufferedImage);

                JFrame frame = new JFrame("DrawGraph");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(mainPanel);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                try {
                    ImageIO.write(bufferedImage, "jpeg", new File("test.jpg"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
