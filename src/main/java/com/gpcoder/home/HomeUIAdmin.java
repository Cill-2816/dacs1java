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
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class HomeUIAdmin extends JFrame {

    public HomeUIAdmin() {
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
            String[] sidebarItem = {"Menu", "Reservation", "Accounting"};
            String[] iconPaths = {
                "image/menu.png",
                "image/reservation.png",
                "image/accounting.png"
            };

            Color selectedColor = new Color(255, 87, 34);
            Color defaultColor = new Color(30, 32, 34);
            Color hoverColor = new Color(60, 63, 65);

            List<JButton> buttons = new ArrayList<>();
            final JButton[] selectedButton = {null};

            for (int i = 0; i < sidebarItem.length; i++) {
                String item = sidebarItem[i];
                String iconPath = iconPaths[i];

                JButton button = new JButton(item);
                button.setAlignmentX(Component.CENTER_ALIGNMENT);
                button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
                button.setFocusPainted(false);
                button.setBackground(item.equals("Menu") ? selectedColor : defaultColor);
                button.setForeground(Color.WHITE);
                button.setFont(new Font("Arial", Font.BOLD, 18));
                button.setBorderPainted(false);
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                button.setHorizontalAlignment(SwingConstants.LEFT);
                button.setIconTextGap(15);

                // Set icon
                ImageIcon icon = new ImageIcon(iconPath);
                Image scaledIcon = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
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

            // Đặt mặc định nút "Menu" được chọn
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
        ImageIcon filterIcon = new ImageIcon("image/add.png");
        Image filterImg = filterIcon.getImage().getScaledInstance(60,60, Image.SCALE_SMOOTH);
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

        // ===== Filter Panel =====
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 8)); // Cách trái 20px, top-bottom 8px
        filterPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 20)); // Lề trên 10px, lề trái phải 20px
        filterPanel.setBackground(new Color(24, 26, 27));

        String[] filters = {"All", "Breakfast", "Lunch", "Dinner"};
        String[] icons = {"dish.png", "breakfast.png", "lunch.png", "dinner.png"};
        final RoundedButton[] selectedFilterBtn = {null};

        for (int i = 0; i < filters.length; i++) {
            String filter = filters[i];
            String iconPath = icons[i];

            ImageIcon icon = new ImageIcon("image/" + iconPath);
            Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Icon vừa phải
            icon = new ImageIcon(img);

            RoundedButton filterBtn = new RoundedButton(filter, 30);
            filterBtn.setPreferredSize(new Dimension(170, 60)); // Giữ nguyên chiều rộng bạn đang dùng
            filterBtn.setHorizontalAlignment(SwingConstants.LEFT);
            filterBtn.setIcon(icon);
            filterBtn.setIconTextGap(10);
            filterBtn.setBackground(new Color(44, 47, 51));
            filterBtn.setForeground(Color.WHITE);
            filterBtn.setFont(new Font("Arial", Font.BOLD, 16));
            filterBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            filterBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (selectedFilterBtn[0] != filterBtn) {
                    filterBtn.setBackground(new Color(60, 63, 67));
                }
            }
            public void mouseExited(MouseEvent e) {
                if (selectedFilterBtn[0] != filterBtn) {
                    filterBtn.setBackground(new Color(44, 47, 50));
                }
            }
        });

        filterBtn.addActionListener(e -> {
            if (selectedFilterBtn[0] != null) {
                selectedFilterBtn[0].setBackground(new Color(44, 47, 51));
            }
            selectedFilterBtn[0] = filterBtn;
            filterBtn.setBackground(new Color(255, 87, 34));
            System.out.println(filter + " selected!");
        });

        if (filter.equals("All")) {
            selectedFilterBtn[0] = filterBtn;
            filterBtn.setBackground(new Color(255, 87, 34));
        }

        filterPanel.add(filterBtn);
    }


                // ===== Content Panel (3 cột, xuống hàng tự động) =====
    JPanel contentPanel = new JPanel(new GridLayout(0, 3, 20, 20));
    contentPanel.setBackground(new Color(24, 26, 27));
    contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

    List<MenuItem> menuItems = MenuData.getSampleMenu();
    for (MenuItem item : menuItems) {
        RoundedPanel itemCard = new RoundedPanel(20);
        itemCard.setPreferredSize(new Dimension(230, 300));
        itemCard.setLayout(new BorderLayout());
        itemCard.setBackground(new Color(36, 40, 45));
        itemCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(50, 54, 58), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        itemCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // ===== Title =====
        JLabel title = new JLabel(item.getName());
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Description =====
        JTextPane desc = new JTextPane();
        desc.setContentType("text/html");
        desc.setText(
            "<html>" +
                "<div style='" +
                    "text-align: center;" +
                    "color: #FF9800;" +
                    "font-family: Arial;" +
                    "font-size: 14px;" +
                    "font-weight: bold;" +
                "'>" +
                    item.getDescription() +
                "</div>" +
            "</html>"
        );
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
            foodImage = new ImageIcon(); // fallback
        }
        JLabel imageLabel = new JLabel(foodImage);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ===== Edit/Delete Buttons =====
        ImageIcon editIcon = new ImageIcon(new ImageIcon("image/edit.png").getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH));
        ImageIcon deleteIcon = new ImageIcon(new ImageIcon("image/delete.png").getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH));

        JButton editButton = new JButton(editIcon);
        JButton deleteButton = new JButton(deleteIcon);

        Color defaultBtnColor = new Color(36, 40, 45);
        Color hoverBtnColor = new Color(60, 63, 65);
        Color pressedBtnColor = new Color(84, 88, 95);

        for (JButton button : new JButton[]{editButton, deleteButton}) {
            button.setPreferredSize(new Dimension(42, 42));
            button.setBackground(defaultBtnColor);
            button.setContentAreaFilled(true);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(hoverBtnColor);
                }
                public void mouseExited(MouseEvent e) {
                    button.setBackground(defaultBtnColor);
                }
                public void mousePressed(MouseEvent e) {
                    button.setBackground(pressedBtnColor);
                }
                public void mouseReleased(MouseEvent e) {
                    button.setBackground(button.contains(e.getPoint()) ? hoverBtnColor : defaultBtnColor);
                }
            });
        }

        JPanel priceAndControlPanel = new JPanel(new BorderLayout());
        priceAndControlPanel.setOpaque(false);
        priceAndControlPanel.add(price, BorderLayout.CENTER);
        priceAndControlPanel.add(editButton, BorderLayout.WEST);
        priceAndControlPanel.add(deleteButton, BorderLayout.EAST);

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
        details.add(priceAndControlPanel);

        itemCard.add(details, BorderLayout.CENTER);
        contentPanel.add(itemCard);
    }

        // ===== ScrollPane trực tiếp chứa contentPanel =====
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setPreferredSize(new Dimension(0, 600)); // chiều cao đủ 2 hàng
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // cuộn mượt

        // Custom thanh cuộn
        scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new DarkScrollBarUI()); // nếu muốn dùng ngang
        scrollPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new JPanel() {{
            setOpaque(false);           // lấp góc trống, cùng màu nền
        }});




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

        // middlePanel chứa filter + scroll content
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(new Color(24, 26, 27));
        middlePanel.add(filterPanel, BorderLayout.NORTH);
        middlePanel.add(scrollPane, BorderLayout.CENTER); // scrollPane thay cho contentPanel

        leftSide.add(middlePanel, BorderLayout.CENTER);

        // Top bar chứa thông tin người dùng
        JPanel contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(new Color(24, 26, 27));
        contentArea.add(topBar, BorderLayout.NORTH);

        // centerArea gồm phần trái (menu/filter/content) và phải (order)
        JPanel centerArea = new JPanel(new BorderLayout());
        centerArea.setBackground(new Color(24, 26, 27));
        centerArea.add(leftSide, BorderLayout.CENTER);
        centerArea.add(orderPanel, BorderLayout.EAST);

        // contentArea gồm topBar + centerArea
        contentArea.add(centerArea, BorderLayout.CENTER);

        // mainPanel gồm sidebar trái và phần còn lại
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);

        // Gán vào cửa sổ chính
        setContentPane(mainPanel);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomeUIAdmin::new);
    }
}
