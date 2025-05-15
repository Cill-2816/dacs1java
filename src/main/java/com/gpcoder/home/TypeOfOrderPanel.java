package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
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
    private boolean isDineInSelected = true;

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

        // mặc định chọn dine-in
        updateSelection(true);

        dineInButton.addActionListener(e -> updateSelection(true));
        takeOutButton.addActionListener(e -> updateSelection(false));

        JPanel optionWrapper = new JPanel();
        optionWrapper.setLayout(new BoxLayout(optionWrapper, BoxLayout.Y_AXIS));
        optionWrapper.setOpaque(false);
        optionWrapper.setBorder(new EmptyBorder(0, 0, 15, 0));
        optionWrapper.add(dineInButton);
        optionWrapper.add(Box.createVerticalStrut(15));
        optionWrapper.add(takeOutButton);
        add(optionWrapper, BorderLayout.CENTER);

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
            String type = isDineInSelected ? "dinein" : "takeout";
            System.out.println("Selected order type: " + type);
            cardLayout.show(parentPanel, "customer");
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private RoundedButton createOptionButton(String text, String iconPath) {
        RoundedButton button = new RoundedButton(text, 25);
        button.setPreferredSize(new Dimension(260, 80));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Arial", Font.BOLD, 22));
        button.setForeground(Color.WHITE);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 20, 10, 20));

        // resize icon
        ImageIcon rawIcon = new ImageIcon(iconPath);
        Image scaledImage = rawIcon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImage));
        button.setIconTextGap(15);

        return button;
    }

    private void updateSelection(boolean dineInSelected) {
        this.isDineInSelected = dineInSelected;
        dineInButton.setBackground(dineInSelected ? new Color(66, 70, 77) : new Color(44, 47, 51));
        takeOutButton.setBackground(!dineInSelected ? new Color(66, 70, 77) : new Color(44, 47, 51));
    }
}
