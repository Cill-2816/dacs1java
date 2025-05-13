package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.gpcoder.model.MenuItem;

public class ItemCard extends RoundedPanel {
    private final MenuItem item;
    private final JLabel qty;

    public ItemCard(MenuItem item) {
        super(20);
        this.item = item;

        setPreferredSize(new Dimension(230, 300));
        setLayout(new BorderLayout());
        setBackground(new Color(36, 40, 45));
        Border outer = BorderFactory.createLineBorder(new Color(50, 54, 58), 1);
        Border inner = BorderFactory.createEmptyBorder(25, 10, 10, 10);
        setBorder(BorderFactory.createCompoundBorder(outer, inner));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // ===== Title =====
        JLabel title = new JLabel(item.getName());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Description =====
        JTextPane desc = new JTextPane();
        desc.setContentType("text/html");
        desc.setText("<html><div style='text-align: center;color: #9c9c9c;font-family: Arial;font-size: 12px;font-weight: bold;'>" + item.getDescription() + "</div></html>");
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
            foodImage = new ImageIcon();
        }
        JLabel imageLabel = new JLabel(foodImage);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Panel chứa nút trừ/cộng và số lượng =====
        JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        control.setBackground(new Color(36, 40, 45));

        ImageIcon minusIcon = new ImageIcon("image/minus.png");
        ImageIcon plusIcon = new ImageIcon("image/plus.png");
        Image scaledMinus = minusIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        Image scaledPlus = plusIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
        minusIcon = new ImageIcon(scaledMinus);
        plusIcon = new ImageIcon(scaledPlus);

        CircleButton minus = new CircleButton(minusIcon, new Color(52, 53, 56), 32);
        minus.setHoverColor(Color.decode("#5A5B5E"));
        CircleButton plus = new CircleButton(plusIcon, new Color(235, 87, 87), 32);
        plus.setHoverColor(Color.decode("#FF6B6B"));

        qty = new JLabel("0");
        qty.setForeground(Color.WHITE);
        qty.setFont(new Font("Arial", Font.BOLD, 14));

        minus.addActionListener(e -> {
            int count = Integer.parseInt(qty.getText());
            if (count > 0) qty.setText(String.valueOf(count - 1));
        });

        plus.addActionListener(e -> {
            int count = Integer.parseInt(qty.getText());
            qty.setText(String.valueOf(count + 1));
        });

        control.add(minus);
        control.add(qty);
        control.add(plus);

        // ===== Panel chứa tất cả thành phần =====
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
        details.add(control);

        add(details, BorderLayout.CENTER);
    }
} 
