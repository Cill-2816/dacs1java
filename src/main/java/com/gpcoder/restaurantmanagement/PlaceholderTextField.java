package com.gpcoder.restaurantmanagement;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;

import javax.swing.JTextField;

public class PlaceholderTextField extends JTextField {
    private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        setForeground(Color.WHITE);
        setCaretColor(Color.WHITE); // con trỏ trắng
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty()) { // ❗ KHÔNG kiểm tra focus
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.GRAY);
            g2.setFont(new Font("Arial", Font.ITALIC, 14));
            Insets insets = getInsets();
            FontMetrics fm = g2.getFontMetrics();
            int x = insets.left + 5;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;

            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }

    public String getActualText() {
        return getText();
    }
}
