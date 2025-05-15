package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class CustomerInforPanel extends JPanel {

    private JTextField nameField;
    private JTextField dateField;
    private JTextField timeField;
    private JLabel guestLabel;

    public CustomerInforPanel(CardLayout cardLayout, JPanel parentPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 32, 34));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel title = new JLabel("Customer Information", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        // --- FORM ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(getBackground());

        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(createInputField("Name", nameField = new JTextField("Kamalesh Roy")));
        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(createGuestSelector());
        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(createInputFieldWithIcon("Date", dateField = new JTextField("06/04/2024"), "\uD83D\uDCC5")); // 📅
        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(createInputFieldWithIcon("Time", timeField = new JTextField("10:00 PM"), "\u23F0")); // ⏰

        add(formPanel, BorderLayout.CENTER);

        // --- BUTTONS ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(getBackground());
        buttonPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        RoundedButton backButton = new RoundedButton("Back", 20);
        backButton.setPreferredSize(new Dimension(120, 42));
        backButton.setBackground(getBackground());
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "type"));

        RoundedButton nextButton = new RoundedButton("Next", 20);
        nextButton.setPreferredSize(new Dimension(120, 42));
        nextButton.setBackground(new Color(255, 87, 34));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(e -> cardLayout.show(parentPanel, "payment"));

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Field: Name (hoặc field nhập bất kỳ)
    private JPanel createInputField(String label, JTextField field) {
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    container.setBackground(getBackground());
    container.setOpaque(true);
    container.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel lbl = new JLabel(label);
    lbl.setForeground(Color.LIGHT_GRAY);
    lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

    // Rounded field
    RoundedPanel fieldPanel = new RoundedPanel(18);
    fieldPanel.setLayout(new BorderLayout());
    fieldPanel.setBackground(new Color(44, 47, 51));
    fieldPanel.setBorder(new EmptyBorder(10, 16, 10, 16));
    fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    // Sửa max width cho fieldPanel
    fieldPanel.setMaximumSize(new Dimension(320, 48)); // Giới hạn chiều rộng

    field.setBorder(null);
    field.setBackground(new Color(44, 47, 51));
    field.setForeground(Color.WHITE);
    field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    field.setCaretColor(Color.WHITE);
    field.setPreferredSize(new Dimension(220, 26));
    field.setMaximumSize(new Dimension(220, 26)); // Giới hạn chiều rộng thực tế

    fieldPanel.add(field, BorderLayout.CENTER);

    container.add(lbl);
    container.add(Box.createVerticalStrut(7));
    container.add(fieldPanel);
    // Giới hạn luôn chiều rộng container
    container.setMaximumSize(new Dimension(350, 60));
    return container;
}


    // Field có icon (Date, Time)
    private JPanel createInputFieldWithIcon(String label, JTextField field, String iconText) {
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    container.setBackground(getBackground());
    container.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel lbl = new JLabel(label);
    lbl.setForeground(Color.LIGHT_GRAY);
    lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
    lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

    RoundedPanel fieldPanel = new RoundedPanel(18);
    fieldPanel.setLayout(new BorderLayout());
    fieldPanel.setBackground(new Color(44, 47, 51));
    fieldPanel.setBorder(new EmptyBorder(10, 16, 10, 16));
    fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    // Giới hạn chiều rộng
    fieldPanel.setMaximumSize(new Dimension(320, 48));

    field.setBorder(null);
    field.setBackground(new Color(44, 47, 51));
    field.setForeground(Color.WHITE);
    field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    field.setCaretColor(Color.WHITE);
    field.setPreferredSize(new Dimension(120, 26));
    field.setMaximumSize(new Dimension(120, 26));

    JLabel icon = new JLabel(iconText);
    icon.setForeground(new Color(200, 200, 200));
    icon.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 18));
    icon.setHorizontalAlignment(SwingConstants.RIGHT);
    icon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    icon.setBorder(new EmptyBorder(0, 8, 0, 0));

    fieldPanel.add(field, BorderLayout.CENTER);
    fieldPanel.add(icon, BorderLayout.EAST);

    container.add(lbl);
    container.add(Box.createVerticalStrut(7));
    container.add(fieldPanel);
    container.setMaximumSize(new Dimension(350, 60));
    return container;
}


    // Guest selector
    private JPanel createGuestSelector() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(getBackground());
        container.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel("Number of Guest");
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPanel selector = new RoundedPanel(18);
        selector.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 6));
        selector.setBackground(new Color(44, 47, 51));

        JButton minus = createCircleButton("-");
        JButton plus = createCircleButton("+");
        guestLabel = new JLabel("4");
        guestLabel.setForeground(Color.WHITE);
        guestLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));

        minus.addActionListener(e -> {
            int val = Integer.parseInt(guestLabel.getText());
            if (val > 1) guestLabel.setText(String.valueOf(val - 1));
        });
        plus.addActionListener(e -> {
            int val = Integer.parseInt(guestLabel.getText());
            guestLabel.setText(String.valueOf(val + 1));
        });

        selector.add(minus);
        selector.add(guestLabel);
        selector.add(plus);

        container.add(lbl);
        container.add(Box.createVerticalStrut(7));
        container.add(selector);
        return container;
    }

    private JButton createCircleButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        btn.setBackground(new Color(255, 87, 34));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
