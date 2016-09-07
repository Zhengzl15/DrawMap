package cc.gauto;

import cc.gauto.db.DBHelper;
import cc.gauto.db.entity.ExamEntity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Rodrigo
 */
public class GraphPanel extends JPanel {

    private int width = 800;
    private int heigth = 400;
    private int padding = 25;
    private int labelPadding = 25;
    private Color lineColor = new Color(44, 102, 230, 180);
    private Color pointColor = new Color(100, 100, 100, 180);
    private Color gridColor = new Color(200, 200, 200, 200);
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private int pointWidth = 4;
    private int numberYDivisions = 10;
    private List<Double> scores;

    private int screenWidth = 1920;
    private int screenHeight = 1080;
    private BufferedImage showImage = null;

    public GraphPanel() {
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(showImage, 0, 0, null);
    }

    public void setShowImage(final BufferedImage bufferedImage) {
        this.showImage = bufferedImage;
    }

    public BufferedImage getImage(){
        BufferedImage bufferedImage = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.setColor(Color.GRAY);
        g2.fillRect(0, 0, 1920, 1080);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        return bufferedImage;
    }

}
