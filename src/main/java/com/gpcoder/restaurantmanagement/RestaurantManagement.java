package com.gpcoder.restaurantmanagement;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RestaurantManagement extends JFrame {

    public RestaurantManagement() {
        setTitle("Restaurant Login");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // Panel bên trái với ảnh và slogan
        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(new Color(255, 94, 58));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("★ ★ ★ BEST ★ ★ ★");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel("RESTAURANT");
        subTitle.setFont(new Font("Arial", Font.BOLD, 28));
        subTitle.setForeground(Color.WHITE);
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("IN THIS CITY");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        descLabel.setForeground(Color.WHITE);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel imageLabel = new JLabel(new ImageIcon("C:\\Users\\DUC ANH\\Pictures\\Saved Pictures\\ChatGPT Image 08_10_54 23 thg 4, 2025.png")); // thay bằng đường dẫn ảnh thực
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(titleLabel);
        leftPanel.add(subTitle);
        leftPanel.add(descLabel);
        leftPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        leftPanel.add(imageLabel);
        leftPanel.add(Box.createVerticalGlue());

        // Panel bên phải với form login
        JPanel rightPanel = new JPanel();
        rightPanel.setBackground(new Color(30, 30, 30));
        rightPanel.setLayout(null);

        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(140, 40, 200, 30);
        rightPanel.add(welcomeLabel);

        JLabel userLabel = new JLabel("User Name");
        userLabel.setForeground(Color.WHITE);
        userLabel.setBounds(100, 100, 200, 20);
        rightPanel.add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(100, 125, 250, 30);
        rightPanel.add(userField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setBounds(100, 170, 200, 20);
        rightPanel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(100, 195, 250, 30);
        rightPanel.add(passField);

        JCheckBox rememberCheck = new JCheckBox("Remember me");
        rememberCheck.setBounds(100, 235, 150, 20);
        rememberCheck.setForeground(Color.WHITE);
        rememberCheck.setOpaque(false);
        rightPanel.add(rememberCheck);

        JButton loginButton = new JButton("Log In");
        loginButton.setBounds(100, 270, 250, 40);
        loginButton.setBackground(new Color(255, 94, 58));
        loginButton.setForeground(Color.WHITE);
        rightPanel.add(loginButton);

        add(leftPanel);
        add(rightPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new RestaurantManagement();
        System.out.println("12345");
    }
}
