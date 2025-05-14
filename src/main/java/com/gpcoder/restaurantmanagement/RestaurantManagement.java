package com.gpcoder.restaurantmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import com.gpcoder.home.HomeUI;
import com.gpcoder.security.Hashpassword;

public class RestaurantManagement extends JFrame {

    private JLabel imageLabel;
    private Image originalImage;

    // Thêm các ảnh vào mảng này
    private String[] imagePaths = {
        "image/login1.png",
        "image/login2.png",
        "image/login3.png",
        "image/login4.png"
    };
    private int currentImageIndex = 0;
    private Timer imageTimer;

    public RestaurantManagement() {
        setTitle("Restaurant Login");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Left panel
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(30, 30, 30));

        // Load ảnh đầu tiên
        originalImage = new ImageIcon(imagePaths[0]).getImage();
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        leftPanel.add(imageLabel, BorderLayout.CENTER);

        // Tự động scale ảnh khi panel thay đổi kích thước
        leftPanel.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent evt) {
                updateImage(leftPanel.getWidth(), leftPanel.getHeight());
            }
        });

        // Right panel
        JPanel rightPanel = createRightPanel();

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setResizeWeight(0.66);
        splitPane.setDividerSize(0);
        splitPane.setEnabled(false);

        add(splitPane);

        // Gắn tỉ lệ chia động cho split pane khi resize
        bindSplitRatio(splitPane, 2.0 / 3.0);

        // Bắt đầu slideshow ảnh
        startImageSlideshow(leftPanel);

        setVisible(true);
    }

    private void updateImage(int width, int height) {
        if (width > 0 && height > 0 && originalImage != null) {
            Image scaled = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));
        }
    }

    private void startImageSlideshow(JPanel leftPanel) {
        imageTimer = new Timer(3000, e -> {
            currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
            originalImage = new ImageIcon(imagePaths[currentImageIndex]).getImage();
            updateImage(leftPanel.getWidth(), leftPanel.getHeight());
        });
        imageTimer.start();
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        // Logo
        JLabel logoLabel = new JLabel();
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        ImageIcon logoIcon = new ImageIcon("image/logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(logoImage));
        gbc.gridy = 0;
        panel.add(logoLabel, gbc);

        // Welcome
        JLabel welcomeLabel = new JLabel("Welcome Back!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridy = 1;
        panel.add(welcomeLabel, gbc);

        // Username
        gbc.gridy = 2;
        PlaceholderTextField userfield = new PlaceholderTextField("Username");
        panel.add(createInputField("image/user_icon.png", userfield), gbc);

        // Password
        gbc.gridy = 3;
        PlaceholderPasswordField passfield = new PlaceholderPasswordField("Password");
        panel.add(createInputField("image/lock_icon.png", passfield), gbc);


        // Remember me
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.LINE_START;
        JCheckBox rememberCheck = new JCheckBox("Remember username");
        rememberCheck.setFont(new Font("Arial", Font.PLAIN, 16));
        rememberCheck.setForeground(Color.WHITE);
        rememberCheck.setBackground(new Color(30, 30, 30)); // cho chắc ăn nền khớp panel
        rememberCheck.setBorder(null);
        rememberCheck.setFocusPainted(false); // <<< thêm dòng này để tắt focus border
        rememberCheck.setOpaque(false);
        panel.add(rememberCheck, gbc);


        // Login button
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setBackground(new Color(255, 94, 58));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBorder(null);
        loginButton.setPreferredSize(new Dimension(300, 50));
        panel.add(loginButton, gbc);

        passfield.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
		try {
                    String password = passfield.getActualText();
                    String username = userfield.getActualText();
                    Hashpassword a = new Hashpassword();
                    if (a.verifyUser(username, password)) {
                        JOptionPane.showMessageDialog(null,
					"Login successful!", 
					"Successfully", 
					JOptionPane.WARNING_MESSAGE);

                    } else {
                        JOptionPane.showMessageDialog(null,
					"Incorrect username or password!",
					"Warning", 
					JOptionPane.WARNING_MESSAGE);

                    }
		} catch (Exception e2) {
                    System.out.println(e2);
		}
            }
        });
        loginButton.addActionListener(new ActionListener() {	
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String username = userfield.getActualText();
                    String password = passfield.getActualText();

                    Hashpassword hasher = new Hashpassword();
                    if (hasher.verifyUser(username, password)) {
                        JOptionPane.showMessageDialog(
                            null,
                            "Login successful!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                        );

                        SwingUtilities.invokeLater(() -> new HomeUI(username));
                        dispose(); // Close the login window
                    } else {
                        JOptionPane.showMessageDialog(
                            null,
                            "Incorrect username or password!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE
                        );
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                        null,
                        "An error occurred during login!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });


        return panel;
    }

    private JPanel createInputField(String iconPath, JTextField textField) {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout(8, 0));
        inputPanel.setBackground(new Color(50, 50, 50));
        inputPanel.setPreferredSize(new Dimension(300, 40));
    
        // Icon
        JLabel iconLabel = new JLabel();
        iconLabel.setIcon(new ImageIcon(new ImageIcon(iconPath).getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
        iconLabel.setPreferredSize(new Dimension(40, 40));
        iconLabel.setHorizontalAlignment(JLabel.CENTER);
    
        // TextField style
        textField.setBackground(new Color(50, 50, 50));
        textField.setBorder(null);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Arial", Font.PLAIN, 16));
        textField.setCaretColor(Color.WHITE);
    
        // Add to panel
        inputPanel.add(iconLabel, BorderLayout.WEST);
        inputPanel.add(textField, BorderLayout.CENTER);
    
        return inputPanel;
    }
    

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
        // Session session = HibernateUtils.getSessionFactory().openSession();
        // Transaction tx = session.beginTransaction();
        // tx.commit();
        // session.close();

        // session = HibernateUtils.getSessionFactory().openSession();
        // List <Staff> staffs = session.createQuery("from Staff", Staff.class).list();
        // session.close();
        
        // for (Staff s : staffs)
        // {
        //     System.out.println(s);
        // }
    }
}
