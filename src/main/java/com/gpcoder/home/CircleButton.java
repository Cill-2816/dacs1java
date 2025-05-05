package com.gpcoder.home;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.JButton;

public class CircleButton extends JButton {
    private final Color defaultColor;
    private Color hoverColor;
    private final int diameter;
    private boolean isHovered = false;

    public CircleButton(Icon icon, Color bgColor, int diameter) {
        super(icon);
        this.defaultColor = bgColor;
        this.hoverColor = bgColor.brighter(); // mặc định hover là sáng hơn
        this.diameter = diameter;

        setPreferredSize(new Dimension(diameter, diameter));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Thêm lắng nghe hover
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovered = false;
                repaint();
            }
        });
    }

    // Cho phép set hover color thủ công nếu muốn
    public void setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dùng màu hover nếu đang hover
        g2.setColor(isHovered ? hoverColor : defaultColor);
        g2.fillOval(0, 0, getWidth(), getHeight());

        // Vẽ icon giữa nút
        Icon icon = getIcon();
        if (icon != null) {
            int iconWidth = icon.getIconWidth();
            int iconHeight = icon.getIconHeight();
            int x = (getWidth() - iconWidth) / 2;
            int y = (getHeight() - iconHeight) / 2;
            icon.paintIcon(this, g2, x, y);
        }

        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = diameter / 2;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        return Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2) <= Math.pow(radius, 2);
    }
}
