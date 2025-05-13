package com.gpcoder.home;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.gpcoder.model.MenuItem;

public class AdminItemCard extends RoundedPanel {

    public AdminItemCard(MenuItem item) {
        super(20);
        setPreferredSize(new Dimension(230, 300));
        setLayout(new BorderLayout());
        setBackground(new Color(36, 40, 45));
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 54, 58), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // ===== Title =====
        JLabel title = new JLabel(item.getName());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Description =====
        JTextPane desc = new JTextPane();
        desc.setContentType("text/html");
        desc.setText("<html><div style='text-align: center; color: #9c9c9c; font-family: Arial; font-size: 12px; font-weight: bold;'>" +
            item.getDescription() + "</div></html>");
        desc.setEditable(false);
        desc.setOpaque(false);
        desc.setBorder(null);
        desc.setAlignmentX(Component.CENTER_ALIGNMENT);
        desc.setMaximumSize(new Dimension(Integer.MAX_VALUE, Short.MAX_VALUE));

        // ===== Price =====
        JLabel price = new JLabel(item.getPrice());
        price.setForeground(Color.WHITE);
        price.setFont(new Font("Arial", Font.BOLD, 18));
        price.setHorizontalAlignment(SwingConstants.CENTER);

        // ===== Food Image =====
        ImageIcon foodImage;
        try {
            foodImage = new ImageIcon(item.getImagePath());
            Image scaledImage = foodImage.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
            foodImage = new ImageIcon(scaledImage);
        } catch (Exception e) {
            foodImage = new ImageIcon(); // fallback
        }
        JLabel imageLabel = new JLabel(foodImage);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Edit/Delete Buttons =====
        ImageIcon editIcon = new ImageIcon(new ImageIcon("image/edit.png").getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH));
        ImageIcon deleteIcon = new ImageIcon(new ImageIcon("image/delete.png").getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH));

        JButton editButton = new JButton(editIcon);
        JButton deleteButton = new JButton(deleteIcon);

        Color defaultBtnColor = new Color(36, 40, 45);
        Color hoverBtnColor = new Color(60, 63, 65);
        Color pressedBtnColor = new Color(84, 88, 95);

        for (JButton button : new JButton[]{editButton, deleteButton}) {
            button.setPreferredSize(new Dimension(42, 42));
            button.setBackground(defaultBtnColor);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(hoverBtnColor);
                }
                public void mouseExited(MouseEvent e) {
                    button.setBackground(defaultBtnColor);
                }
                public void mousePressed(MouseEvent e) {
                    button.setBackground(pressedBtnColor);
                }
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(button.contains(e.getPoint()) ? hoverBtnColor : defaultBtnColor);
                }
            });
        }

        JPanel priceAndControlPanel = new JPanel(new BorderLayout());
        priceAndControlPanel.setOpaque(false);
        priceAndControlPanel.add(price, BorderLayout.CENTER);
        priceAndControlPanel.add(editButton, BorderLayout.WEST);
        priceAndControlPanel.add(deleteButton, BorderLayout.EAST);

        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
        details.setOpaque(false);
        details.add(Box.createVerticalStrut(10));
        details.add(imageLabel);
        details.add(Box.createVerticalStrut(10));
        details.add(title);
        details.add(Box.createVerticalStrut(5));
        details.add(desc);
        details.add(Box.createVerticalStrut(10));
        details.add(priceAndControlPanel);

        add(details, BorderLayout.CENTER);
    }
}
