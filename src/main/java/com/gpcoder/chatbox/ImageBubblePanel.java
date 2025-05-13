package com.gpcoder.chatbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ImageBubblePanel extends JPanel {
    private final File sourceFile;

    public ImageBubblePanel(String filename, long fileSizeBytes, File sourceFile, boolean isMine, boolean isContinuation, LocalDateTime timesent) {
        this.sourceFile = sourceFile;
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(isContinuation ? 2 : 8, 12, 2, 12));

        // === Bubble ===
        JPanel bubble = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                super.paintComponent(g);
            }
        };
        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setBackground(new Color(60, 63, 65));
        bubble.setOpaque(false);
        bubble.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        bubble.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

        // === Load và thu nhỏ ảnh để hiển thị ===
        ImageIcon originalIcon = new ImageIcon(sourceFile.getAbsolutePath());
        Image scaledImage = originalIcon.getImage().getScaledInstance(250, -1, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setMaximumSize(new Dimension(400, Integer.MAX_VALUE)); // hoặc kích thước bạn mong muốn

        bubble.add(imageLabel);

        // === Thời gian gửi ===
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        // JLabel timeLabel = new JLabel(timesent.format(formatter));
        // timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        // timeLabel.setForeground(new Color(200, 200, 200));
        // timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);

        // JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        // timePanel.setOpaque(false);
        // timePanel.add(timeLabel);
        // bubble.add(Box.createVerticalStrut(4));
        // bubble.add(timePanel);

        // === Căn trái/phải theo người gửi ===
        JPanel wrapper = new JPanel(new FlowLayout(isMine ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(bubble);

        if (!isMine) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);

            if (!isContinuation) {
                JLabel avatar = new JLabel(new ImageIcon(
                        new ImageIcon("image/avata.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
                avatar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

                JPanel avatarPanel = new JPanel(new BorderLayout());
                avatarPanel.setOpaque(false);
                avatarPanel.add(avatar, BorderLayout.NORTH);
                row.add(avatarPanel, BorderLayout.WEST);
            } else {
                JPanel placeholder = new JPanel();
                placeholder.setOpaque(false);
                placeholder.setPreferredSize(new Dimension(40, 1));
                row.add(placeholder, BorderLayout.WEST);
            }

            row.add(wrapper, BorderLayout.CENTER);
            add(row, BorderLayout.CENTER);
        } else {
            add(wrapper, BorderLayout.CENTER);
        }

        // === Sự kiện khi click để mở ảnh lớn ===
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showImageFullScreen();
            }
        });
    }

    private void showImageFullScreen() {
        JFrame frame = new JFrame("Xem ảnh");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        ImageIcon fullSizeIcon = new ImageIcon(sourceFile.getAbsolutePath());
        JLabel fullImageLabel = new JLabel(fullSizeIcon);
        JScrollPane scrollPane = new JScrollPane(fullImageLabel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton downloadBtn = new JButton("Tải ảnh");
        downloadBtn.addActionListener(e -> downloadFile());
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(downloadBtn);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void downloadFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(sourceFile.getName()));
        int result = chooser.showSaveDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File destFile = chooser.getSelectedFile();
            try (InputStream in = new FileInputStream(sourceFile);
                 OutputStream out = new FileOutputStream(destFile)) {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                JOptionPane.showMessageDialog(this, "Tải ảnh thành công!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Tải ảnh thất bại.");
            }
        }
    }
}
