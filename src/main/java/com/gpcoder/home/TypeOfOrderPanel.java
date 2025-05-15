package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TypeOfOrderPanel extends JPanel {

    private final RoundedButton dineInButton;
    private final RoundedButton takeOutButton;
    private JPanel tableCardPanel;
    private CardLayout tableCardLayout;

    private final Color selectedColor = new Color(66, 70, 77);
    private final Color defaultColor = new Color(44, 47, 51);

    public TypeOfOrderPanel(CardLayout cardLayout, JPanel parentPanel) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 0));
        setBackground(new Color(30, 32, 34));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Type of Order", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(new EmptyBorder(10, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        dineInButton = createOptionButton("Dine-in", "image/dinein.png");
        takeOutButton = createOptionButton("Take-out", "image/takeout.png");

        dineInButton.addActionListener(e -> updateSelection(true));
        takeOutButton.addActionListener(e -> updateSelection(false));

        JPanel optionWrapper = new JPanel();
        optionWrapper.setLayout(new BoxLayout(optionWrapper, BoxLayout.Y_AXIS));
        optionWrapper.setOpaque(false);
        optionWrapper.setBorder(new EmptyBorder(0, 0, 15, 0));
        optionWrapper.add(dineInButton);
        optionWrapper.add(Box.createVerticalStrut(15));
        optionWrapper.add(takeOutButton);

        // ===== Table Panel with CardLayout =====
        tableCardLayout = new CardLayout();
        tableCardPanel = new JPanel(tableCardLayout);
        tableCardPanel.setOpaque(false);

        JPanel emptyPanel = new JPanel();
        emptyPanel.setOpaque(false);

        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setOpaque(false);

        // First Floor
        JLabel level1Label = new JLabel("First Floor");
        level1Label.setForeground(Color.LIGHT_GRAY);
        level1Label.setFont(new Font("Arial", Font.BOLD, 16));
        level1Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(Box.createVerticalStrut(15));
        tablePanel.add(level1Label);

        JPanel level1Grid = new JPanel(new GridLayout(4, 5, 10, 10));
        level1Grid.setOpaque(false);
        level1Grid.setBorder(new EmptyBorder(10, 0, 10, 0));
        for (int i = 1; i <= 20; i++) {
            level1Grid.add(createTableButton(i));
        }
        tablePanel.add(level1Grid);

        // Second Floor
        JLabel level2Label = new JLabel("Second Floor");
        level2Label.setForeground(Color.LIGHT_GRAY);
        level2Label.setFont(new Font("Arial", Font.BOLD, 16));
        level2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        tablePanel.add(Box.createVerticalStrut(10));
        tablePanel.add(level2Label);

        JPanel level2Grid = new JPanel(new GridLayout(4, 5, 10, 10));
        level2Grid.setOpaque(false);
        level2Grid.setBorder(new EmptyBorder(10, 0, 20, 0));
        for (int i = 21; i <= 40; i++) {
            level2Grid.add(createTableButton(i));
        }
        tablePanel.add(level2Grid);

        tableCardPanel.add(emptyPanel, "empty");
        tableCardPanel.add(tablePanel, "table");

        optionWrapper.add(tableCardPanel);
        add(optionWrapper, BorderLayout.CENTER);

        // ===== Button Panel =====
        RoundedButton backButton = new RoundedButton("Back", 20);
        backButton.setPreferredSize(new Dimension(95, 40));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "order"));

        RoundedButton nextButton = new RoundedButton("Next", 20);
        nextButton.setPreferredSize(new Dimension(95, 40));
        nextButton.setBackground(new Color(255, 87, 34));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Arial", Font.BOLD, 14));
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(e -> {
            String type = dineInButton.getBackground().equals(selectedColor) ? "dinein" : "takeout";
            System.out.println("Selected order type: " + type);
            cardLayout.show(parentPanel, "customer");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Ban đầu chưa chọn gì
        updateSelection(null);
    }

    private RoundedButton createOptionButton(String text, String iconPath) {
        RoundedButton button = new RoundedButton(text, 25);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));

        ImageIcon rawIcon = new ImageIcon(iconPath);
        Image scaledImage = rawIcon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setIconTextGap(15);
        button.setBackground(defaultColor);

        return button;
    }

    private RoundedButton createTableButton(int tableNumber) {
        RoundedButton tableBtn = new RoundedButton(String.valueOf(tableNumber), 15);
        tableBtn.setPreferredSize(new Dimension(50, 40));
        tableBtn.setFocusPainted(false);
        tableBtn.setFont(new Font("Arial", Font.BOLD, 13));
        tableBtn.setForeground(Color.WHITE);
        tableBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tableBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        if (tableNumber % 6 == 0) {
            tableBtn.setBackground(new Color(244, 67, 54)); // đỏ - có khách
            tableBtn.setToolTipText("Occupied");
        } else if (tableNumber % 5 == 0) {
            tableBtn.setBackground(new Color(76, 175, 80)); // xanh - đặt trước
            tableBtn.setToolTipText("Reserved");
        } else {
            tableBtn.setBackground(new Color(158, 158, 158)); // xám - trống
            tableBtn.setToolTipText("Available");
        }

        return tableBtn;
    }

    private void updateSelection(Boolean dineInSelected) {
        dineInButton.setBackground(defaultColor);
        takeOutButton.setBackground(defaultColor);

        if (dineInSelected == null) {
            tableCardLayout.show(tableCardPanel, "empty");
        } else if (dineInSelected) {
            dineInButton.setBackground(selectedColor);
            tableCardLayout.show(tableCardPanel, "table");
        } else {
            takeOutButton.setBackground(selectedColor);
            tableCardLayout.show(tableCardPanel, "empty");
        }

        revalidate();
        repaint();
    }
}
