package com.gpcoder.restaurantmanagement;

import javax.swing.*;
import java.awt.*;

public class PlaceholderPasswordField extends JPasswordField {
    private String placeholder;
    private Color placeholderColor = Color.GRAY;

    public PlaceholderPasswordField(String placeholder) {
        this.placeholder = placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public void setPlaceholderColor(Color color) {
        this.placeholderColor = color;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getPassword().length == 0 && !isFocusOwner() && placeholder != null) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setFont(getFont().deriveFont(Font.ITALIC));
            g2.setColor(placeholderColor);
            Insets insets = getInsets();
            int x = insets.left + 2;
            int y = getHeight() / 2 + getFont().getSize() / 2 - 2;
            g2.drawString(placeholder, x, y);
            g2.dispose();
        }
    }
}
