package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PaymentPanel extends JPanel {

    public PaymentPanel(CardLayout cardLayout, JPanel parentPanel) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 0));
        setBackground(new Color(30, 32, 34));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("Items Details", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerWrapper = new JPanel(new BorderLayout());
        centerWrapper.setBackground(new Color(30, 32, 34));
        centerWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel placeholderWrapper = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        placeholderWrapper.setBackground(new Color(44, 47, 51));
        placeholderWrapper.setOpaque(false);
        placeholderWrapper.setLayout(new BorderLayout());
        placeholderWrapper.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        centerWrapper.add(placeholderWrapper, BorderLayout.CENTER);
        add(centerWrapper, BorderLayout.CENTER);

        RoundedButton backButton = new RoundedButton("Back", 20);
        backButton.setPreferredSize(new Dimension(95, 40));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        backButton.addActionListener(e -> {
            cardLayout.show(parentPanel, "customer");
        });

        RoundedButton doneButton = new RoundedButton("Done", 20);
        doneButton.setPreferredSize(new Dimension(95, 40));
        doneButton.setBackground(new Color(255, 87, 34));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(new Font("Arial", Font.BOLD, 14));
        doneButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 32, 34));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(backButton);
        buttonPanel.add(doneButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
