package com.gpcoder.chatbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

public class BubblePanel extends JPanel {
    public BubblePanel(String sender, String message, boolean isMine, boolean isContinuation, LocalDateTime timesent) {
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
            }
        };
        int maxBubbleWidth = 600; // bạn có thể điều chỉnh theo chiều rộng chat (1/2 khoảng 1440px)

        bubble.setLayout(new BoxLayout(bubble, BoxLayout.Y_AXIS));
        bubble.setBackground(isMine ? new Color(0, 132, 255) : new Color(60, 63, 65));
        bubble.setOpaque(true);
        bubble.setForeground(Color.WHITE);
        bubble.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        bubble.setMaximumSize(new Dimension(maxBubbleWidth + 30, Integer.MAX_VALUE));

        // === Tên người gửi (ẩn nếu là continuation hoặc của mình) ===
        // if (!isContinuation && !isMine) {
        //     JLabel senderLabel = new JLabel(sender);
        //     senderLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        //     senderLabel.setForeground(new Color(180, 180, 180));
        //     bubble.add(senderLabel);
        //     bubble.add(Box.createVerticalStrut(4));
        // }
        
        // === Nội dung ===
        JTextPane msgLabel = new JTextPane() {
    @Override
    public Dimension getPreferredSize() {
        setSize(new Dimension(Integer.MAX_VALUE, Short.MAX_VALUE));
        Dimension d = super.getPreferredSize();

        if (d.width > maxBubbleWidth) {
            setSize(new Dimension(maxBubbleWidth, Short.MAX_VALUE));
            d = super.getPreferredSize();
            d.width = maxBubbleWidth;
        }

        return d;
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        return false;
    }
};

        msgLabel.setEditorKit(new WrapAnywhereKit());
        msgLabel.setText(message);
        msgLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        msgLabel.setForeground(Color.WHITE);
        msgLabel.setOpaque(false);
        msgLabel.setEditable(false);
        msgLabel.setFocusable(false);
        msgLabel.setBorder(null);
        bubble.add(msgLabel); 
        




        // === Thời gian ===
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        JLabel timeLabel = new JLabel(timesent.format(formatter));
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        timeLabel.setForeground(new Color(200, 200, 200));
        timeLabel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        timePanel.setOpaque(false);
        timePanel.add(timeLabel);

        bubble.add(Box.createVerticalStrut(4));
        bubble.add(timePanel);


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
    }
}
