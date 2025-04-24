package com.gpcoder.restaurantmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class RestaurantManagement extends JFrame {

    private JLabel imageLabel;
    private Image originalImage;

    public RestaurantManagement() {
        setTitle("Restaurant Login");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(30, 30, 30));

        // Load and scale image dynamically
        ImageIcon originalIcon = new ImageIcon("image/login.png");
        originalImage = originalIcon.getImage();
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // Resize image automatically with panel
        leftPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                int w = leftPanel.getWidth();
                int h = leftPanel.getHeight();
                if (w > 0 && h > 0) {
                    Image scaled = originalImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
                    imageLabel.setIcon(new ImageIcon(scaled));
                }
            }
        });

        // Right panel
        JPanel rightPanel = createRightPanel();

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.66); // Tỷ lệ chia ban đầu
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false); // Ngăn kéo divider

        add(splitPane);

        // Gắn tỷ lệ động cho split pane khi resize
        bindSplitRatio(splitPane, 2.0 / 3.0);

        setVisible(true);
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
    
        // Logo image
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon logoIcon = new ImageIcon("image/logo.png"); // Đảm bảo file logo.png nằm trong thư mục image/
        Image logoImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(logoImage));
        gbc.gridy = 0;
        panel.add(logoLabel, gbc);
    
        // Welcome text
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        panel.add(welcomeLabel, gbc);
    
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Arial", Font.BOLD, 18));
        userLabel.setForeground(Color.WHITE);
        panel.add(userLabel, gbc);
    
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        JTextField userField = new JTextField(20);
        userField.setForeground(Color.WHITE);
        userField.setBackground(new Color(50, 50, 50));
        userField.setBorder(null);
        userField.setPreferredSize(new Dimension(0, 35));
        panel.add(userField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.LINE_END;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Arial", Font.BOLD, 18));
        passLabel.setForeground(Color.WHITE);
        panel.add(passLabel, gbc);
    
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        JPasswordField passField = new JPasswordField(20);
        passField.setBackground(new Color(50, 50, 50));
        passField.setBorder(null);
        passField.setForeground(Color.WHITE);
        passField.setPreferredSize(new Dimension(0, 35));
        panel.add(passField, gbc);
    
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.LINE_START;
        JCheckBox rememberCheck = new JCheckBox("Remember username");
        rememberCheck.setFont(new Font("Arial", Font.PLAIN, 16));
        rememberCheck.setForeground(Color.WHITE);
        rememberCheck.setBorder(null);
        rememberCheck.setOpaque(false);
        panel.add(rememberCheck, gbc);
    
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(255, 94, 58));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(null);
        loginButton.setPreferredSize(new Dimension(300, 50));
        panel.add(loginButton, gbc);
    
        return panel;
    }
    

    // Gắn tỉ lệ chia động cho split pane
    private void bindSplitRatio(JSplitPane splitPane, double ratio) {
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                splitPane.setDividerLocation((int) (getWidth() * ratio));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RestaurantManagement::new);
    }
}
