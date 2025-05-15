package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class PaymentPanel extends JPanel {

    public PaymentPanel(CardLayout cardLayout, JPanel parentPanel) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(300, 0)); // GIỮ KÍCH THƯỚC NHƯ CŨ
        setBackground(new Color(30, 32, 34));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 10));

        // Tiêu đề
        JLabel title = new JLabel("Items Details", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 1, 0));
        add(title, BorderLayout.NORTH);

        // Center chứa danh sách item + tổng kết bill
        JPanel centerWrapper = new JPanel();
        centerWrapper.setLayout(new BoxLayout(centerWrapper, BoxLayout.Y_AXIS));
        centerWrapper.setBackground(new Color(30, 32, 34));

        // Danh sách món ăn (Fake data, thay bằng list động nếu cần)
        for (int i = 0; i < 3; i++) {
            centerWrapper.add(createItemRow());
            centerWrapper.add(Box.createVerticalStrut(8));
        }

        // Tổng kết bill
        centerWrapper.add(makeSummaryPanel());

        // Đưa vào scroll để không bị tràn nếu nhiều món
        JScrollPane scrollPane = new JScrollPane(centerWrapper, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(30, 32, 34));
        scrollPane.setPreferredSize(new Dimension(300, 290));
        add(scrollPane, BorderLayout.CENTER);

        // Thanh toán (payment method) + Nút dưới
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBackground(new Color(30, 32, 34));

        
        // Phương thức thanh toán
        JPanel paymentPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        paymentPanel.setBackground(new Color(30, 32, 34));
        paymentPanel.setBorder(new EmptyBorder(8, 0, 8, 0));
        paymentPanel.add(createPayButton("Cash", "image/cash.png"));
        paymentPanel.add(createPayButton("QRCode", "image/qrcode.png"));


        bottomPanel.add(paymentPanel, BorderLayout.NORTH);

        // Nút back/done giữ như cũ, không đổi kích thước
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(30, 32, 34));
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
        RoundedButton backButton = new RoundedButton("Back", 20);
        backButton.setPreferredSize(new Dimension(95, 40));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "customer"));
        RoundedButton doneButton = new RoundedButton("Done", 20);
        doneButton.setPreferredSize(new Dimension(95, 40));
        doneButton.setBackground(new Color(255, 87, 34));
        doneButton.setForeground(Color.WHITE);
        doneButton.setFont(new Font("Arial", Font.BOLD, 14));
        doneButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        buttonPanel.add(backButton);
        buttonPanel.add(doneButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private JPanel createItemRow() {
        JPanel row = new JPanel();
        row.setLayout(new BorderLayout(7, 0));
        row.setBackground(new Color(44, 47, 51));
        row.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));
        row.setMaximumSize(new Dimension(280, 52));
        row.setPreferredSize(new Dimension(280, 52));

        // Ảnh mẫu
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(40, 40));
        // Có thể thay ảnh thật, ở đây dùng background màu vàng làm mẫu
        Image img = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = ((BufferedImage) img).createGraphics();
        g2.setColor(new Color(255, 193, 7));
        g2.fillOval(0, 0, 40, 40);
        g2.dispose();
        imageLabel.setIcon(new ImageIcon(img));

        // Thông tin món
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(new Color(44, 47, 51));
        JLabel name = new JLabel("Southwest Scramble Bowl");
        name.setFont(new Font("Arial", Font.BOLD, 13));
        name.setForeground(Color.WHITE);
        JLabel detail = new JLabel("$17.65  x1");
        detail.setFont(new Font("Arial", Font.PLAIN, 11));
        detail.setForeground(new Color(200, 200, 200));
        infoPanel.add(name);
        infoPanel.add(detail);

        JLabel total = new JLabel("$17.65");
        total.setFont(new Font("Arial", Font.BOLD, 13));
        total.setForeground(Color.WHITE);

        row.add(imageLabel, BorderLayout.WEST);
        row.add(infoPanel, BorderLayout.CENTER);
        row.add(total, BorderLayout.EAST);

        return row;
    }

    private JPanel makeSummaryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(44, 47, 51));
        panel.setBorder(new EmptyBorder(10, 12, 10, 12));
        panel.setMaximumSize(new Dimension(280, 85));

        panel.add(makeSummaryRow("Items (3)", "$70.60"));
        panel.add(Box.createVerticalStrut(4));
        panel.add(makeSummaryRow("Tax (5%)", "$1.75"));
        panel.add(Box.createVerticalStrut(7));

        // Total Amount
        JPanel totalRow = new JPanel(new BorderLayout());
        totalRow.setBackground(new Color(44, 47, 51));
        JLabel totalLabel = new JLabel("Total Amount");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        totalLabel.setForeground(Color.WHITE);
        JLabel totalValue = new JLabel("$71.83");
        totalValue.setFont(new Font("Arial", Font.BOLD, 14));
        totalValue.setForeground(Color.WHITE);
        totalRow.add(totalLabel, BorderLayout.WEST);
        totalRow.add(totalValue, BorderLayout.EAST);

        panel.add(totalRow);
        return panel;
    }

    private JPanel makeSummaryRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(44, 47, 51));
        JLabel l = new JLabel(label);
        l.setFont(new Font("Arial", Font.PLAIN, 12));
        l.setForeground(new Color(200, 200, 200));
        JLabel v = new JLabel(value);
        v.setFont(new Font("Arial", Font.PLAIN, 12));
        v.setForeground(new Color(200, 200, 200));
        row.add(l, BorderLayout.WEST);
        row.add(v, BorderLayout.EAST);
        return row;
    }

    private JPanel createPayButton(String text, String imagePath) {
    JPanel p = new JPanel();
    p.setBackground(new Color(44, 47, 51));
    p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
    p.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    // Tạo icon từ đường dẫn ảnh
    ImageIcon icon = new ImageIcon(imagePath);
    Image scaledImg = icon.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
    JLabel iconLabel = new JLabel(new ImageIcon(scaledImg));
    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    JLabel label = new JLabel(text, SwingConstants.CENTER);
    label.setFont(new Font("Arial", Font.BOLD, 11));
    label.setForeground(Color.WHITE);
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    p.add(iconLabel);
    p.add(label);
    return p;
}

}
