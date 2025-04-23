package com.gpcoder.restaurantmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RestaurantManagement extends JFrame {

    public RestaurantManagement() {
        setTitle("Restaurant Login");
        setSize(1080, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // Left panel with image
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(30, 30, 30));
        leftPanel.setLayout(new BorderLayout());

        ImageIcon originalIcon = new ImageIcon("C:\\Users\\DUC ANH\\Pictures\\Saved Pictures\\ChatGPT Image 08_15_39 23 thg 4, 2025.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(520, 520, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // Right panel
        JPanel rightPanelWrapper = new JPanel(new GridBagLayout());
        rightPanelWrapper.setBackground(new Color(30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 30));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        rightPanelWrapper.add(welcomeLabel, gbc);

        gbc.gridy = 1;
        JLabel subLabel = new JLabel("Please enter your details");
        subLabel.setFont(new Font("Arial", Font.BOLD, 14));
        subLabel.setForeground(Color.LIGHT_GRAY);
        subLabel.setHorizontalAlignment(JLabel.CENTER);
        rightPanelWrapper.add(subLabel, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel userLabel = new JLabel("User Name:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        rightPanelWrapper.add(userLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        JTextField userField = new JTextField(15);
        rightPanelWrapper.add(userField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 16));
        passLabel.setForeground(Color.WHITE);
        rightPanelWrapper.add(passLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        JPasswordField passField = new JPasswordField(15);
        rightPanelWrapper.add(passField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        JCheckBox rememberCheck = new JCheckBox("Remember me");
        rememberCheck.setFont(new Font("Arial", Font.BOLD, 14));
        rememberCheck.setForeground(Color.WHITE);
        rememberCheck.setOpaque(false);
        rightPanelWrapper.add(rememberCheck, gbc);

        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setBackground(new Color(255, 94, 58));
        loginButton.setForeground(Color.WHITE);
        loginButton.setPreferredSize(new Dimension(250, 40));
        rightPanelWrapper.add(loginButton, gbc);

        add(leftPanel);
        add(rightPanelWrapper);

        setVisible(true);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantManagement::new);
    }
}
