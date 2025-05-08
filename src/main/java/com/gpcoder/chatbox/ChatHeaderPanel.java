package com.gpcoder.chatbox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChatHeaderPanel extends JPanel {
    private JLabel avatarLabel;
    private JLabel nameLabel;
    private JLabel statusLabel;

    public ChatHeaderPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(new Color(36, 40, 45));
        setPreferredSize(new Dimension(0, 60)); // cao 60px

        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(42, 42));

        nameLabel = new JLabel("Select a user");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);

        statusLabel = new JLabel("Offline");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(Color.LIGHT_GRAY);

        JPanel nameBox = new JPanel();
        nameBox.setLayout(new BoxLayout(nameBox, BoxLayout.Y_AXIS));
        nameBox.setOpaque(false);
        nameBox.add(nameLabel);
        nameBox.add(statusLabel);

        add(avatarLabel);
        add(nameBox);
    }

    public void setUser(User user, boolean online) {
        nameLabel.setText(user.getName());
        statusLabel.setText(online ? "Online" : "Offline");

        try {
            BufferedImage raw = ImageIO.read(new File(user.getAvatarPath()));
            Image scaled = raw.getScaledInstance(42, 42, Image.SCALE_SMOOTH);
            BufferedImage rounded = new BufferedImage(42, 42, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = rounded.createGraphics();
            g2.setClip(new Ellipse2D.Float(0, 0, 42, 42));
            g2.drawImage(scaled, 0, 0, null);
            g2.dispose();
            avatarLabel.setIcon(new ImageIcon(rounded));
        } catch (Exception e) {
            avatarLabel.setIcon(null);
        }
    }
}
