package cc.gauto.generator;

import cc.gauto.db.DBHelper;
import cc.gauto.db.entity.ExamEntity;
import cc.gauto.db.entity.PolygonEntity;
import cc.gauto.db.entity.RoadEntity;
import cc.gauto.db.entity.SubjectEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Zhilong Zheng
 * @Email zhengzl0715@163.com
 * @Date 2016-09-06 22:14
 */
public class ImageGenerator {
    private static ImageGenerator imageGenerator = null;

    //应该保存到数据库
    private double scaleWidth = 1;
    private double scaleHeight = 1;
    private double originX = 0.0;
    private double originY = 0.0;

    private Graphics2D g2 = null;
    private Color solidLineColor = Color.RED;

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
        g2 = bufferedImage.createGraphics();   //这个Graphics2D对象会传到后面去
        //背景
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, imageWidth, imageHeight);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        DBHelper dbHelper = DBHelper.getInstance(dbUrl);
        List<ExamEntity> exams = dbHelper.getExam();
        //先做测试，使用id 110001
        int examId = 110001;
        //使用exam polygon第一点为坐标原点，进行坐标的变换
        PolygonEntity examPolygon = dbHelper.getPolygonById(examId);
        //计算坐标缩放因子
        double distance1 = Math.sqrt(Math.pow(examPolygon.verteces[0] - examPolygon.verteces[2], 2) + Math.pow(examPolygon.verteces[1] - examPolygon.verteces[3], 2)); //p1, p2
        double distance2 = Math.sqrt(Math.pow(examPolygon.verteces[2] - examPolygon.verteces[4], 2) + Math.pow(examPolygon.verteces[3] - examPolygon.verteces[5], 2)); //p2, p3
        if (distance1 > distance2) {
            scaleWidth = imageWidth / distance1;
            scaleHeight = imageHeight / distance2;
        } else {
            scaleWidth = imageWidth / distance2;
            scaleHeight = imageHeight / distance1;
        }
        originX = examPolygon.verteces[0];
        originY = examPolygon.verteces[1];
        //subject
        List<SubjectEntity> subjectEntities = dbHelper.getSubjectByExamId(examId);
        for (SubjectEntity subjectEntity : subjectEntities) {
            List<RoadEntity> roadEntities = dbHelper.getRoadBySubjectId(subjectEntity.polygonId);
            switch (SubjectType.fromInt(subjectEntity.type)) {
                case Daoku:
                    for (RoadEntity polygonEntity : roadEntities) {
                        PolygonEntity polygon = dbHelper.getPolygonById(polygonEntity.polygonId);
                        drawoDaoku(polygon.verteces);
                    }
                    break;
                case Cefang:
                    for (RoadEntity polygonEntity : roadEntities) {
                        PolygonEntity polygon = dbHelper.getPolygonById(polygonEntity.polygonId);
                        drawCefang(polygon.verteces);
                    }
                    break;
                case Zhijiao:
                    for (RoadEntity polygonEntity : roadEntities) {
                        PolygonEntity polygon = dbHelper.getPolygonById(polygonEntity.polygonId);
                        drawZhijiao(polygon.verteces);
                    }
                    break;
                case Quxian:
                    for (RoadEntity polygonEntity : roadEntities) {
                        PolygonEntity polygon = dbHelper.getPolygonById(polygonEntity.polygonId);
                        drawQuxian(polygon.verteces);
                    }
                    break;
                case Poqi:
                    for (RoadEntity polygonEntity : roadEntities) {
                        PolygonEntity polygon = dbHelper.getPolygonById(polygonEntity.polygonId);
                        drawPoqi(polygon.verteces);
                    }
                    break;
                case Youzhijiao:
                    for (RoadEntity polygonEntity : roadEntities) {
                        PolygonEntity polygon = dbHelper.getPolygonById(polygonEntity.polygonId);
                        drawYouZhijiao(polygon.verteces);
                    }
                    break;
            }
        }

        //g2.setColor(Color.RED);
        //g2.drawLine(100, 100, 700, 700);
//        try {
//            Image img = ImageIO.read(new File("pic.jpg"));
//            g2.drawImage(img, 10, 10, 100, 100, null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return bufferedImage;
    }

    /* *绘制倒库的图形
       *Args:
       *      vertices: 用绘制的点
     */

    private void drawoDaoku(double[] vertices) {
        drawSolidLine(solidLineColor, vertices, new short[]{0,1, 1,2, 2,3, 3,4, 4,5, 5,6, 6,7, 7,8, 8,9, 9,0});
    }

    private void drawCefang(double[] vertices) {
        drawSolidLine(solidLineColor, vertices, new short[]{0,1, 1,2, 2,3, 3,4, 4,5, 5,6, 6,7, 7,8, 8,9, 9,0});
    }

    private void drawZhijiao(double[] vertices) {
        drawSolidLine(solidLineColor, vertices, new short[] {0,1, 1,2, 2,3, 3,4, 4,5, 5, 0});
    }

    private void drawQuxian(double[] vertices) {
        short[] index = new short[58*2];
        short count = 0;
        for (short i = 0; i < 57; ++i) {
            index[2*i + 0] = count;
            index[2*i + 1] = (short)(count + 1);
            count ++;
        }
        index[58*2 - 2] = 57;
        index[58*2 - 1] = 0;
        drawSolidLine(solidLineColor, vertices, index);
    }

    private void drawPoqi(double[] vertices) {
        drawSolidLine(solidLineColor, vertices, new short[]{0,1, 1,2, 2,3, 3,4, 4,5, 5,6, 6,7, 7,8, 8,9, 9,10, 10,11, 11,0});
    }

    private void drawYouZhijiao(double[] vertices) {
        drawSolidLine(solidLineColor, vertices, new short[] {0,1, 1,2, 2,3, 3,4, 4,5, 5, 0});
    }

    //绘制实线, index表示下标, 1, 2, 2, 3表示1-2, 2-3
    private void drawSolidLine(Color lineColr, double[] vertices, short[] index) {
        g2.setColor(lineColr);
        if (index.length % 2 != 0) {
            return;
        }
        for (int i = 0; i < index.length; i += 2) {
            int t1 = index[i];
            int t2 = index[i] + 1;
            int t3 = 2*index[i];
            int t4 = 2*index[i] + 1;
            int t5 = 2*index[i+1];
            int t6 = 2*index[i+1] + 1;
            double p1X = (vertices[2*index[i]] - originX) * scaleWidth;
            double p1Y = (vertices[2*index[i] + 1] - originY) * scaleHeight;
            double p2X = (vertices[2*index[i+1]]- originX) * scaleWidth;
            double p2Y = (vertices[2*index[i+1] + 1]- originY) * scaleHeight;
            g2.drawLine((int)p1X, (int)p1Y, (int)p2X, (int)p2Y);
            System.out.println(t3);
        }
    }

}
