package com.gpcoder.chatbox;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class UserCellRenderer extends JPanel implements ListCellRenderer<User> {
    private JLabel avatarLabel;
    private JLabel nameLabel;
    private final Map<String, ImageIcon> avatarCache = new HashMap<>(); // Cache ảnh đã load

    public UserCellRenderer() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 6));
        setOpaque(true);

        avatarLabel = new JLabel();
        avatarLabel.setPreferredSize(new Dimension(36, 36));

        nameLabel = new JLabel();
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        nameLabel.setForeground(Color.WHITE);

        add(avatarLabel);
        add(nameLabel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends User> list, User user,
                                                int index, boolean isSelected, boolean cellHasFocus) {
        String path = user.getAvatarPath();
        ImageIcon icon = avatarCache.get(path);

        if (icon == null && path != null) {
            try {
                BufferedImage raw = ImageIO.read(new File(path));
                Image scaled = raw.getScaledInstance(36, 36, Image.SCALE_SMOOTH);
                BufferedImage circle = makeRoundedImage(scaled, 36);
                icon = new ImageIcon(circle);
                avatarCache.put(path, icon); // Lưu vào cache
            } catch (Exception e) {
                icon = null; // fallback null nếu lỗi ảnh
            }
        }

        avatarLabel.setIcon(icon);
        nameLabel.setText(user.getName());

        setBackground(isSelected ? new Color(60, 120, 200) : new Color(44, 47, 51));

        return this;
    }

    // Hàm tạo avatar tròn
    private BufferedImage makeRoundedImage(Image img, int size) {
        BufferedImage rounded = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = rounded.createGraphics();
        g2.setClip(new Ellipse2D.Float(0, 0, size, size));
        g2.drawImage(img, 0, 0, size, size, null);
        g2.dispose();
        return rounded;
    }
}
