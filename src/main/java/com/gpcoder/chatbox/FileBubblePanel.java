package com.gpcoder.chatbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FileBubblePanel extends JPanel {
    private final File sourceFile;
    public FileBubblePanel(String filename, long fileSizeBytes, File sourceFile, boolean isMine, boolean isContinuation, LocalDateTime timesent) {
        this.sourceFile = sourceFile;
        setLayout(new BorderLayout());
        setOpaque(false);

        // Giảm khoảng cách nếu là continuation
        setBorder(BorderFactory.createEmptyBorder(isContinuation ? 2 : 8, 12, 2, 12));

        // === Bubble chính ===
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
        bubble.setForeground(Color.WHITE);
        bubble.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        bubble.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));

        // === Nội dung (có icon) ===
        ImageIcon icon = new ImageIcon("image/iconfile.jpg");
        Image scaled = icon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        JLabel msgLabel = new JLabel(filename, new ImageIcon(scaled), JLabel.LEFT);
        msgLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        msgLabel.setForeground(Color.WHITE);
        msgLabel.setOpaque(false);
        bubble.add(msgLabel);

        // === Kích thước ===
        JLabel filesizeLabel = new JLabel(formatSize(fileSizeBytes));
        filesizeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        filesizeLabel.setForeground(Color.GRAY);
        filesizeLabel.setOpaque(false);
        bubble.add(filesizeLabel);

        // === Thời gian ===
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        JLabel timeLabel = new JLabel(timesent.format(formatter));
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        timeLabel.setForeground(new Color(200, 200, 200));
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        bubble.add(Box.createVerticalStrut(4));
        bubble.add(timeLabel);

        // === Bọc căn trái/phải ===
        JPanel wrapper = new JPanel(new FlowLayout(isMine ? FlowLayout.RIGHT : FlowLayout.LEFT, 0, 0));
        wrapper.setOpaque(false);
        wrapper.add(bubble);

        // === Xử lý avatar người khác hoặc placeholder ===
        if (!isMine) {
            JPanel row = new JPanel(new BorderLayout());
            row.setOpaque(false);

            if (!isContinuation) {
                // Avatar thực
                JLabel avatar = new JLabel(new ImageIcon(
                    new ImageIcon("image/avata.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH)));
                avatar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 8));

                JPanel avatarPanel = new JPanel(new BorderLayout());
                avatarPanel.setOpaque(false);
                avatarPanel.add(avatar, BorderLayout.NORTH);

                row.add(avatarPanel, BorderLayout.WEST);
            } else {
                // Placeholder để căn đều
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

        // 👉 Click để tải file
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                downloadFile();
            }
        });
    }

    private void downloadFile() {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(sourceFile.getName())); // gợi ý tên gốc
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
                JOptionPane.showMessageDialog(this, "Tải file thành công!");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Tải file thất bại.");
            }
        }
    }

    private String formatSize(long bytes) {
        if (bytes >= 1024 * 1024) return String.format("%.2f MB", bytes / 1024.0 / 1024);
        else if (bytes >= 1024) return String.format("%.2f KB", bytes / 1024.0);
        else return bytes + " B";
    }
}