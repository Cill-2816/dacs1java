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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.xml.bind.JAXBException;

import com.gpcoder.Utils.XMLUtil;
import com.gpcoder.chatbox.InternalChatUI;
import com.gpcoder.model.MenuItem;
import com.gpcoder.model.Staff;
import com.gpcoder.model_xml.StaffList;

public class HomeUI extends JFrame {

    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private JPanel contentPanel;
    JScrollPane scrollPane;
    private List<MenuItem> menuitem;

    public HomeUI(String username) {
        setTitle("Mr. Chefs - Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1440, 900);
        setLocationRelativeTo(null);
        UI(username);
        setVisible(true);
    }

    public void UI(String username) {
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
            String[] sidebarItem = {"All", "Breakfast", "Lunch", "Dinner"};
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

            for (int i = 0; i < sidebarItem.length; i++) {
                String item = sidebarItem[i];
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

            // ===== Profile, Logout và Chat Button =====
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
            Image scaledProfile = profileIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH); // resize = 28x28
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
            Image scaledLogout = logoutIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH); // resize = 28x28
            logoutButton.setIcon(new ImageIcon(scaledLogout));

            // Chat Button
            JButton chatButton = new JButton("Internal Chat");
            chatButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            chatButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
            chatButton.setBackground(defaultColor);
            chatButton.setForeground(Color.WHITE);
            chatButton.setFont(new Font("Arial", Font.BOLD, 17));
            chatButton.setBorderPainted(false);
            chatButton.setFocusPainted(false);
            chatButton.setHorizontalAlignment(SwingConstants.LEFT);
            chatButton.setIconTextGap(15);

            chatButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> new InternalChatUI(username));
            }
            });

            // Set chat icon - Resize giống profile
            ImageIcon chatIcon = new ImageIcon("image/chat.png");
            Image scaledChat = chatIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH); // resize = 28x28
            chatButton.setIcon(new ImageIcon(scaledChat));
            

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

             // Hover effect for Profile
            Color profileOriginalColor = profileButton.getBackground();
            profileButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    profileButton.setBackground(hoverColor);
                }
                public void mouseExited(MouseEvent e) {
                    profileButton.setBackground(profileOriginalColor);
                }
            });

             // Hover effect for Chat
            Color chatOriginalColor = chatButton.getBackground();
            chatButton.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    chatButton.setBackground(hoverColor);
                }
                public void mouseExited(MouseEvent e) {
                    chatButton.setBackground(chatOriginalColor);
                }
            });
            

            // Add Profile + Logout vào panel
            profileLogoutPanel.add(chatButton);
            profileLogoutPanel.add(profileButton);
            profileLogoutPanel.add(logoutButton);

            // Add Profile-Logout panel vào sidebar
            sidebar.add(profileLogoutPanel);

        // ===== Top Bar =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(36, 40, 45));
        topBar.setPreferredSize(new Dimension(getWidth(), 60));

        // Tên người dùng
        String staffname = "";
        try {
            StaffList staffs = XMLUtil.loadFromXml(new File("data/staff.xml"), StaffList.class);
            List <Staff> st = staffs.getStaff();
            for (Staff a : st) if (a.getId().equals(username)) staffname = a.getFirstname() + " " + a.getLastname();
        } catch (JAXBException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        ImageIcon icon = new ImageIcon("image/avata.png"); 
        Image scaledImage = icon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        icon = new ImageIcon(scaledImage);
        JLabel userInfo = new JLabel(staffname);
        userInfo.setIcon(icon);
        userInfo.setHorizontalTextPosition(SwingConstants.RIGHT); // Text bên phải icon
        userInfo.setVerticalTextPosition(SwingConstants.CENTER);
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
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<MenuItem> matched = searchByName(menuitem, searchField.getText());
                showMenu(matched); 

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
            contentPanel = new JPanel(new GridLayout(0, 3, 20, 20));
            contentPanel.setBackground(new Color(24, 26, 27));
            contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ===== ScrollPane trực tiếp chứa contentPanel =====
        scrollPane = new JScrollPane(contentPanel);
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
        

        new Thread(this::runNetworking).start();
        SwingUtilities.invokeLater(() -> { scrollPane.getVerticalScrollBar().setValue(0);  // Đưa về đầu
        });
        //=== Order Panel (CÁC PANEL CARDLAYOUT) ===
        // === CardLayout để chuyển đổi ===
        CardLayout cardLayout = new CardLayout();
        JPanel contentSwitcher = new JPanel(cardLayout);

        // Truyền cardLayout và contentSwitcher vào panel con
        OrderProcessPanel orderPanel = new OrderProcessPanel(cardLayout, contentSwitcher);
        TypeOfOrderPanel typePanel = new TypeOfOrderPanel(cardLayout, contentSwitcher);
        CustomerInforPanel customerPanel = new CustomerInforPanel(cardLayout, contentSwitcher);
        PaymentPanel paymentPanel = new PaymentPanel(cardLayout, contentSwitcher);

        contentSwitcher.add(orderPanel, "order");
        contentSwitcher.add(typePanel, "type");
        contentSwitcher.add(customerPanel, "customer");
        contentSwitcher.add(paymentPanel, "payment");

        // ===== Gộp các panel lại =====
        JPanel leftSide = new JPanel(new BorderLayout());
        leftSide.setBackground(new Color(24, 26, 27));
        leftSide.add(searchPanel, BorderLayout.NORTH);
        
        // middlePanel chứa filter + scroll content
        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(new Color(24, 26, 27));
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
        centerArea.add(contentSwitcher, BorderLayout.EAST);
        
        // contentArea gồm topBar + centerArea
        contentArea.add(centerArea, BorderLayout.CENTER);
        
        // mainPanel gồm sidebar trái và phần còn lại
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);
        
        // Gán vào cửa sổ chính
        setContentPane(mainPanel);
    // Listener cho buttons
        buttons.get(0).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getmenu(true, true, true);
            }
        });

        buttons.get(1).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getmenu(true, false, false);
            }
        });

        buttons.get(2).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getmenu(false, true, false);
            }
        });

        buttons.get(3).addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getmenu(false, false, true);
            }
        });

    }

    public List<MenuItem> searchByName(List<MenuItem> items, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        List<MenuItem> result = new ArrayList<>();

        for (MenuItem item : items) {
            if (item.getName() != null && item.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(item);
            }
        }
        return result;
    }


    private void getmenu(Boolean breakfast, Boolean lunch, Boolean dinner) {
        try {
            if (socket != null && socket.isConnected() && !socket.isOutputShutdown()) {
                outStream.writeObject("GET_MENU:" + Boolean.toString(breakfast) + ":" + Boolean.toString(lunch) + ":" + Boolean.toString(dinner));
                outStream.flush();
            }
        } catch (IOException e) {
            System.err.println("Loi khi gui yeu cau menu: " + e.getMessage());
        }
    }

    private void runNetworking() {
        try {
            this.socket = new Socket("localhost", 12345);
            // this.socket = new Socket("26.106.134.18", 12345);

            this.outStream = new ObjectOutputStream(socket.getOutputStream());
            this.inStream = new ObjectInputStream(socket.getInputStream());

            if (socket != null && socket.isConnected() && !socket.isOutputShutdown()) {
                getmenu(true, true, true);
            }

            while(true) {
                Object obj = inStream.readObject();
                List<MenuItem> list = (List<MenuItem>) obj;
                if (this.menuitem == null || this.menuitem.isEmpty()) {
                    this.menuitem = list;
                }
                showMenu(list);
            }
        } catch (Exception e) {
        }
    }

    private void showMenu(List<MenuItem> list) {
        contentPanel.removeAll();
        for (MenuItem item : list) {
            ItemCard itemCard = new ItemCard(item);
            contentPanel.add(itemCard);
        }

        scrollPane.getVerticalScrollBar().setValue(0);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HomeUI("Chauttn"));
    }
}

