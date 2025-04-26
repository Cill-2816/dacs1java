package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

// =======================
// Bo góc cho JButton
class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        super.paintComponent(g);
        g2.dispose();
    }
}

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
        sidebar.setBackground(new Color(34, 37, 41));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));

        ImageIcon logoIcon = new ImageIcon("image/logo.png");
        Image scaledImage = logoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logoIcon = new ImageIcon(scaledImage);

        JLabel logo = new JLabel(logoIcon);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        logo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        sidebar.add(logo);

        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));

        String[] menuItems = {"Menu", "Table Service", "Reservation", "Delivery", "Accounting"};
        for (String item : menuItems) {
            JButton button = new JButton(item);
            button.setMaximumSize(new Dimension(200, 45));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setFocusPainted(false);
            button.setBackground(item.equals("Menu") ? new Color(255, 87, 34) : new Color(44, 47, 51));
            button.setForeground(Color.WHITE);
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setBorderPainted(false);
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            button.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    button.setBackground(new Color(60, 63, 67));
                }
                public void mouseExited(MouseEvent e) {
                    button.setBackground(item.equals("Menu") ? new Color(255, 87, 34) : new Color(44, 47, 51));
                }
            });
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
            sidebar.add(button);
        }

        sidebar.add(Box.createVerticalGlue());

        JButton profileButton = new JButton("Profile");
        profileButton.setMaximumSize(new Dimension(200, 35));
        profileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        profileButton.setBackground(new Color(44, 47, 51));
        profileButton.setForeground(Color.WHITE);
        profileButton.setFont(new Font("Poppins", Font.PLAIN, 14));
        profileButton.setBorderPainted(false);
        sidebar.add(profileButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setMaximumSize(new Dimension(200, 35));
        logoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoutButton.setBackground(new Color(44, 47, 51));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Poppins", Font.PLAIN, 14));
        logoutButton.setBorderPainted(false);
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(logoutButton);

        // ===== Top Bar =====
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(36, 40, 45));
        topBar.setPreferredSize(new Dimension(getWidth(), 60));

        JLabel userInfo = new JLabel("👤 Kristin Watson (Waiter)  ");
        userInfo.setForeground(Color.WHITE);
        userInfo.setFont(new Font("Poppins", Font.PLAIN, 14));
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

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(filterButton, BorderLayout.EAST);

        // ===== Filter Panel =====
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(24, 26, 27));
        String[] filters = {"All", "Breakfast", "Lunch", "Dinner"};
        String[] icons = {"dish.png", "dish.png", "dish.png", "dish.png"};
        final RoundedButton[] selectedFilterBtn = {null};

        for (int i = 0; i < filters.length; i++) {
            String filter = filters[i];
            String iconPath = icons[i];

            ImageIcon icon = new ImageIcon("image/" + iconPath);
            Image img = icon.getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH);
            icon = new ImageIcon(img);

            RoundedButton filterBtn = new RoundedButton(filter, 30);
            filterBtn.setPreferredSize(new Dimension(200, 60));
            filterBtn.setHorizontalAlignment(SwingConstants.LEFT);
            filterBtn.setIcon(icon);
            filterBtn.setIconTextGap(10);
            filterBtn.setBackground(new Color(44, 47, 51));
            filterBtn.setForeground(Color.WHITE);
            filterBtn.setFont(new Font("Poppins", Font.BOLD, 14));
            filterBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            filterBtn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    if (selectedFilterBtn[0] != filterBtn) {
                        filterBtn.setBackground(new Color(60, 63, 67));
                    }
                }
                public void mouseExited(MouseEvent e) {
                    if (selectedFilterBtn[0] != filterBtn) {
                        filterBtn.setBackground(new Color(44, 47, 51));
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

        // ===== Content Panel =====
        JPanel contentPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        contentPanel.setBackground(new Color(24, 26, 27));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 6; i++) {
            JPanel itemCard = new JPanel();
            itemCard.setLayout(new BoxLayout(itemCard, BoxLayout.Y_AXIS));
            itemCard.setBackground(new Color(36, 40, 45));
            itemCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 54, 58), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
            itemCard.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            JLabel title = new JLabel("Southwest Scramble Bowl");
            title.setForeground(Color.WHITE);
            title.setFont(new Font("Poppins", Font.BOLD, 14));
            title.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel desc = new JLabel("<html><div style='text-align: center;'>Perfectly seasoned scrambled eggs served with toast.</div></html>");
            desc.setForeground(new Color(180, 180, 180));
            desc.setFont(new Font("Poppins", Font.PLAIN, 12));
            desc.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel price = new JLabel("$17.65");
            price.setForeground(Color.WHITE);
            price.setFont(new Font("Poppins", Font.BOLD, 16));
            price.setAlignmentX(Component.CENTER_ALIGNMENT);

            JPanel control = new JPanel(new FlowLayout(FlowLayout.CENTER));
            control.setBackground(new Color(36, 40, 45));
            JButton minus = new JButton("-");
            JLabel qty = new JLabel("0");
            qty.setForeground(Color.WHITE);
            qty.setFont(new Font("Poppins", Font.BOLD, 14));
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

            itemCard.add(title);
            itemCard.add(Box.createRigidArea(new Dimension(0, 5)));
            itemCard.add(desc);
            itemCard.add(Box.createRigidArea(new Dimension(0, 5)));
            itemCard.add(price);
            itemCard.add(Box.createRigidArea(new Dimension(0, 5)));
            itemCard.add(control);

            contentPanel.add(itemCard);
        }

        // ===== Order Panel =====
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BoxLayout(orderPanel, BoxLayout.Y_AXIS));
        orderPanel.setBackground(new Color(30, 32, 34));
        orderPanel.setPreferredSize(new Dimension(300, 0));
        orderPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel orderTitle = new JLabel("Order Process");
        orderTitle.setForeground(Color.WHITE);
        orderTitle.setFont(new Font("Poppins", Font.BOLD, 16));
        orderTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        orderPanel.add(orderTitle);

        String[] steps = {
            "1. Add Food Items",
            "2. Type of Order",
            "3. Select Table",
            "4. Customer Info",
            "5. Payment"
        };

        for (String step : steps) {
            JLabel stepLabel = new JLabel("<html><u>" + step + "</u></html>");
            stepLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            stepLabel.setForeground(Color.LIGHT_GRAY);
            stepLabel.setFont(new Font("Poppins", Font.PLAIN, 14));
            stepLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
            stepLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            stepLabel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, step + " clicked!");
                }
                public void mouseEntered(MouseEvent e) {
                    stepLabel.setForeground(Color.WHITE);
                }
                public void mouseExited(MouseEvent e) {
                    stepLabel.setForeground(Color.LIGHT_GRAY);
                }
            });
            orderPanel.add(stepLabel);
        }

        // ===== Gộp các panel lại =====
        JPanel leftSide = new JPanel(new BorderLayout());
        leftSide.setBackground(new Color(24, 26, 27));
        leftSide.add(searchPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBackground(new Color(24, 26, 27));
        middlePanel.add(filterPanel, BorderLayout.NORTH);
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
