package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class HomeUI extends JFrame {

    public HomeUI() {
        setTitle("Mr. Chefs - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 900);
        setLocationRelativeTo(null);
        UI();
        setVisible(true);
    }

    public void UI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(24, 26, 27));

                // ===== Sidebar =====
            JPanel sidebar = new JPanel();
            sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
            sidebar.setBackground(new Color(30, 32, 34));
            sidebar.setPreferredSize(new Dimension(220, getHeight()));

            // Logo
            ImageIcon logoIcon = new ImageIcon("image/logo.png");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            logoIcon = new ImageIcon(scaledLogo);

            JLabel logo = new JLabel(logoIcon);
            logo.setAlignmentX(Component.CENTER_ALIGNMENT);
            logo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
            sidebar.add(logo);

            // Menu items
            String[] menuItems = {"All", "Breakfast", "Lunch", "Dinner"};
            String[] iconPaths = {
                "image/dish.png",
                "image/breakfast.png",
                "image/lunch.png",
                "image/dinner.png"
            };

            Color selectedColor = new Color(255, 87, 34);
            Color defaultColor = new Color(30, 32, 34);
            Color hoverColor = new Color(60, 63, 65);

            List<JButton> buttons = new ArrayList<>();
            final JButton[] selectedButton = {null}; // lưu nút đang chọn

            for (int i = 0; i < menuItems.length; i++) {
                String item = menuItems[i];
                String iconPath = iconPaths[i];

                JButton button = new JButton(item);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                button.setFocusPainted(false);
                button.setBackground(item.equals("All") ? selectedColor : defaultColor);
                button.setForeground(Color.WHITE);
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.setBorderPainted(false);
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button.setHorizontalAlignment(SwingConstants.LEFT);
                button.setIconTextGap(15);

                // Set icon
                ImageIcon icon = new ImageIcon(iconPath);
                Image scaledIcon = icon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledIcon));

                button.addMouseListener(new MouseAdapter() {
                    public void mouseEntered(MouseEvent e) {
                        if (button != selectedButton[0]) {
                            button.setBackground(hoverColor);
                        }
                    }
                    public void mouseExited(MouseEvent e) {
                        if (button != selectedButton[0]) {
                            button.setBackground(defaultColor);
                        }
                    }
                });

                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (selectedButton[0] != null) {
                            selectedButton[0].setBackground(defaultColor);
                        }
                        button.setBackground(selectedColor);
                        selectedButton[0] = button;
                    }
                });

                buttons.add(button);
                sidebar.add(button);
            }

            // Đặt mặc định nút "All" được chọn
            selectedButton[0] = buttons.get(0);


            // Đẩy profile và logout xuống dưới cùng
            sidebar.add(Box.createVerticalGlue());

            // ===== Profile và Logout =====
            JPanel profileLogoutPanel = new JPanel();
            profileLogoutPanel.setLayout(new BoxLayout(profileLogoutPanel, BoxLayout.Y_AXIS)); // để 2 nút dọc thẳng hàng
            profileLogoutPanel.setBackground(new Color(30, 32, 34));

            // Profile Button
            JButton profileButton = new JButton("Profile");
            profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            profileButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            profileButton.setBackground(defaultColor);
            profileButton.setForeground(Color.WHITE);
            profileButton.setFont(new Font("Arial", Font.BOLD, 17));
            profileButton.setBorderPainted(false);
            profileButton.setFocusPainted(false);
            profileButton.setHorizontalAlignment(SwingConstants.LEFT);
            profileButton.setIconTextGap(15);

            // Set profile icon - Resize đúng size
            ImageIcon profileIcon = new ImageIcon("image/user_icon.png");
            Image scaledProfile = profileIcon.getImage().getScaledInstance(28, 28, Image.SCALE_SMOOTH); // resize = 28x28
            profileButton.setIcon(new ImageIcon(scaledProfile));

            // Logout Button
            JButton logoutButton = new JButton("Logout");
            logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            logoutButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            logoutButton.setBackground(defaultColor);
            logoutButton.setForeground(Color.WHITE);
            logoutButton.setFont(new Font("Arial", Font.BOLD, 17));
            logoutButton.setBorderPainted(false);
            logoutButton.setFocusPainted(false);
            logoutButton.setHorizontalAlignment(SwingConstants.LEFT);
            logoutButton.setIconTextGap(15);

            // Set logout icon - Resize giống profile
            ImageIcon logoutIcon = new ImageIcon("image/logout.png");
            Image scaledLogout = logoutIcon.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH); // resize = 28x28
            logoutButton.setIcon(new ImageIcon(scaledLogout));

            logoutButton.setIcon(new ImageIcon(scaledLogout));

            // Hover effect for Logout
            Color logoutOriginalColor = logoutButton.getBackground();
            logoutButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    logoutButton.setBackground(hoverColor);
                }
                public void mouseExited(MouseEvent e) {
                    logoutButton.setBackground(logoutOriginalColor);
                }
            });

            // Add Profile + Logout vào panel
            profileLogoutPanel.add(profileButton);
            profileLogoutPanel.add(logoutButton);

            // Add Profile-Logout panel vào sidebar
            sidebar.add(profileLogoutPanel);



        // ===== Top Bar =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(36, 40, 45));
        topBar.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel userInfo = new JLabel("👤 Kristin Watson (Waiter)  ");
        userInfo.setForeground(Color.WHITE);
        userInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        userInfo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
        topBar.add(userInfo, BorderLayout.EAST);

        // ===== Search Panel =====
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        searchPanel.setBackground(new Color(24, 26, 27));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        PlaceholderTextField searchField = new PlaceholderTextField("Search a food...");
        searchField.setPreferredSize(new Dimension(400, 40));
        searchField.setFont(new Font("Arial", Font.ITALIC, 14));
        searchField.setBackground(new Color(44, 47, 51));
        searchField.setForeground(Color.GRAY);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Search a food...")) {
                    searchField.setText("");
                    searchField.setForeground(Color.WHITE);
                }
            }
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().trim().isEmpty()) {
                    searchField.setText("Search a food...");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });

        RoundedButton filterButton = new RoundedButton("", 20);
        filterButton.setPreferredSize(new Dimension(50, 40));
        filterButton.setBackground(new Color(44, 47, 51));
        ImageIcon filterIcon = new ImageIcon("image/setting.png");
        Image filterImg = filterIcon.getImage().getScaledInstance(64,64, Image.SCALE_SMOOTH);
        filterButton.setIcon(new ImageIcon(filterImg));
        filterButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        // Hiệu ứng hover và nhấn cho filterButton
        Color defaultFilterColor = new Color(44, 47, 51);
        Color hoverFilterColor = new Color(60, 63, 65);
        Color pressedFilterColor = new Color(84, 88, 95);

        filterButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                filterButton.setBackground(hoverFilterColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                filterButton.setBackground(defaultFilterColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                filterButton.setBackground(pressedFilterColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (filterButton.contains(e.getPoint())) {
                    filterButton.setBackground(hoverFilterColor); // vẫn đang ở trong nút
                } else {
                    filterButton.setBackground(defaultFilterColor); // rời khỏi nút
                }
            }
        });


        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(filterButton, BorderLayout.EAST);

                // ===== Content Panel =====
        JPanel contentPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        contentPanel.setBackground(new Color(24, 26, 27));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 6; i++) {

            RoundedPanel itemCard = new RoundedPanel(20);
            itemCard.setLayout(new BorderLayout());           // BorderLayout để dễ “neo” xuống đáy
            itemCard.setBackground(new Color(36, 40, 45));
            itemCard.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(50, 54, 58), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            itemCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // ----- Thành phần hiển thị -----
            JLabel title = new JLabel("Southwest Scramble Bowl");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Arial", Font.BOLD, 14));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel desc = new JLabel(
                "<html><div style='text-align: center;'>Perfectly seasoned scrambled eggs served with toast.</div></html>");
            desc.setForeground(new Color(180, 180, 180));
            desc.setFont(new Font("Arial", Font.BOLD, 12));
            desc.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel price = new JLabel("$17.65");
            price.setForeground(Color.WHITE);
            price.setFont(new Font("Arial", Font.BOLD, 16));
            price.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
            control.setBackground(new Color(36, 40, 45));
            JButton minus = new JButton("-");
            JLabel qty = new JLabel("0");
            qty.setForeground(Color.WHITE);
            qty.setFont(new Font("Arial", Font.BOLD, 14));
            JButton plus = new JButton("+");

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

            // ----- Panel con xếp dọc -----
            JPanel details = new JPanel();
            details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));
            details.setOpaque(false);                          // kế thừa màu nền của itemCard

            details.add(title);
            details.add(Box.createRigidArea(new Dimension(0, 5)));
            details.add(desc);
            details.add(Box.createRigidArea(new Dimension(0, 5)));
            details.add(price);
            details.add(Box.createRigidArea(new Dimension(0, 5)));
            details.add(control);

            // “Neo” panel con xuống đáy
            itemCard.add(details, BorderLayout.SOUTH);

            contentPanel.add(itemCard);
        }


        // ===== Order Panel =====
        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setPreferredSize(new Dimension(300, 0));
        orderPanel.setBackground(new Color(30, 32, 34));
        orderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title
        JLabel orderTitle = new JLabel("Order Process", SwingConstants.CENTER);
        orderTitle.setForeground(Color.WHITE);
        orderTitle.setFont(new Font("Arial", Font.BOLD, 18));
        orderTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        orderPanel.add(orderTitle, BorderLayout.NORTH);

        // Panel chứa các bước
        JPanel stepsPanel = new JPanel();
        stepsPanel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 dòng, 1 cột, cách nhau 10px
        stepsPanel.setBackground(new Color(30, 32, 34));

        String[] steps = {
            "Add Food Items",
            "Type of Order",
            "Select Table",
            "Customer Info",
            "Payment"
        };

        // Tạo font chung cho dễ chỉnh sửa sau này
        Font stepFont = new Font("Arial", Font.PLAIN, 16);
        Font numberFont = new Font("Arial", Font.BOLD, 14);

        int stepNumber = 1;
    for (String step : steps) {
        JButton stepButton = new JButton();
        stepButton.setBackground(new Color(30, 32, 34));
        stepButton.setFocusPainted(false);
        stepButton.setBorder(BorderFactory.createLineBorder(new Color(30, 32, 34)));
        stepButton.setFont(stepFont);
        stepButton.setOpaque(true);

        // Sắp xếp nội dung dọc
        stepButton.setLayout(new BoxLayout(stepButton, BoxLayout.Y_AXIS));

        // Số thứ tự
        JLabel numberLabel = new JLabel(String.valueOf(stepNumber), SwingConstants.CENTER);
        numberLabel.setForeground(Color.WHITE);
        numberLabel.setFont(numberFont);
        numberLabel.setOpaque(true);
        numberLabel.setBackground(new Color(45, 48, 52));
        numberLabel.setPreferredSize(new Dimension(30, 30));
        numberLabel.setMaximumSize(new Dimension(30, 30));
        numberLabel.setMinimumSize(new Dimension(30, 30));
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Nội dung chữ
        JLabel textLabel = new JLabel(step, SwingConstants.CENTER);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(stepFont);
        textLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0)); // Cách số 8px
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thêm vào button
        stepButton.add(Box.createVerticalGlue());
        stepButton.add(numberLabel);
        stepButton.add(Box.createRigidArea(new Dimension(0, 5)));
        stepButton.add(textLabel);
        stepButton.add(Box.createVerticalGlue());

        // Quan trọng: ép full chiều ngang
        stepButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        stepButton.setAlignmentY(Component.CENTER_ALIGNMENT);
        stepButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        stepButton.setPreferredSize(new Dimension(Short.MAX_VALUE, 80));

        // Hiệu ứng hover (thay đổi màu nền)
        Color defaultBackground = stepButton.getBackground();
        Color hoverBackground = new Color(60, 63, 65); // màu sáng hơn khi hover

        stepButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                stepButton.setBackground(hoverBackground);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                stepButton.setBackground(defaultBackground);
                stepButton.setBorder(BorderFactory.createLineBorder(defaultBackground)); // trở lại như cũ
            }
        });
    stepsPanel.add(stepButton);
    stepNumber++;
}
    orderPanel.add(stepsPanel, BorderLayout.CENTER);

        // ===== Gộp các panel lại =====
        JPanel leftSide = new JPanel(new BorderLayout());
        leftSide.setBackground(new Color(24, 26, 27));
        leftSide.add(searchPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(new Color(24, 26, 27));
        middlePanel.add(contentPanel, BorderLayout.CENTER);

        leftSide.add(middlePanel, BorderLayout.CENTER);

        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(new Color(24, 26, 27));
        contentArea.add(topBar, BorderLayout.NORTH);

        JPanel centerArea = new JPanel(new BorderLayout());
        centerArea.setBackground(new Color(24, 26, 27));
        centerArea.add(leftSide, BorderLayout.CENTER);
        centerArea.add(orderPanel, BorderLayout.EAST);

        contentArea.add(centerArea, BorderLayout.CENTER);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomeUI::new);
    }
}
