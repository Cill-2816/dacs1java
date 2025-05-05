package com.gpcoder.home;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class CircularImage extends JComponent {
    private final BufferedImage img;

    /**
     * @param path  đường dẫn hoặc resource của ảnh
     * @param size  đường kính mong muốn (px)
     */
    public CircularImage(String path, int size) throws Exception {
        BufferedImage original = ImageIO.read(getClass().getResource(path)); // hoặc new File(path)
        // scale ảnh về kích thước mới
        Image scaled = original.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        img = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.drawImage(scaled, 0, 0, null);
        g2.dispose();

        setPreferredSize(new Dimension(size, size));
        setOpaque(false);          // để nền trong suốt
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // bo tròn
        Shape circle = new Ellipse2D.Double(0, 0, getWidth(), getHeight());
        g2.setClip(circle);
        g2.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g2.dispose();
    }
}

