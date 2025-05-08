package com.gpcoder.chatbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class InternalChatUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JList<String> userList;
    private JButton sendButton;

    public InternalChatUI() {
        setTitle("Internal Chat");
        setSize(1440, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // === Dark theme colors ===
        Color bgColor = new Color(44, 47, 51);
        Color panelColor = new Color(44, 47, 51);
        Color textColor = Color.WHITE;

        setLayout(new BorderLayout());

        // === User List Panel ===
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Alice");
        listModel.addElement("Bob");
        listModel.addElement("Charlie");

        userList = new JList<>(listModel);
        userList.setBackground(panelColor);
        userList.setForeground(textColor);
        userList.setSelectionBackground(new Color(70, 130, 180));
        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new Dimension(180, 0));
        add(userScrollPane, BorderLayout.WEST);

        // === Chat Panel ===
        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBackground(bgColor);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(bgColor);
        chatArea.setForeground(textColor);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        // === Input Panel chia làm 3 phần ===
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(panelColor);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // === Left: Nút attach và image ===
        ImageIcon attachIcon = new ImageIcon(new ImageIcon("image/attach.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("image/picture.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        JButton attachButton = new JButton(attachIcon);
        JButton imageButton = new JButton(imageIcon);
        styleFlatButton(attachButton);
        styleFlatButton(imageButton);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        leftPanel.setBackground(panelColor);
        leftPanel.add(attachButton);
        leftPanel.add(imageButton);

        // === Center: Input field ===
        inputField = new JTextField();
        inputField.setBackground(panelColor);
        inputField.setForeground(textColor);
        inputField.setCaretColor(textColor);
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        inputField.addActionListener(e -> sendMessage());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(panelColor);
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 63, 65), 1, true));
        centerPanel.add(inputField, BorderLayout.CENTER);

        // === Right: Nút gửi ===
        ImageIcon sendIcon = new ImageIcon(new ImageIcon("image/send.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        sendButton = new JButton(sendIcon);
        styleFlatButton(sendButton);
        sendButton.addActionListener(e -> sendMessage());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setBackground(panelColor);
        rightPanel.add(sendButton);

        // === Gắn vào inputPanel chính ===
        inputPanel.add(leftPanel, BorderLayout.WEST);
        inputPanel.add(centerPanel, BorderLayout.CENTER);
        inputPanel.add(rightPanel, BorderLayout.EAST);

        chatPanel.add(inputPanel, BorderLayout.SOUTH);
        add(chatPanel, BorderLayout.CENTER);

        // === Gán hiệu ứng hover cho cả 3 nút ===
        Color defaultColor = panelColor;
        Color hoverColor = new Color(60, 63, 65);
        Color pressColor = new Color(84, 88, 95);
        addHoverEffect(sendButton, defaultColor, hoverColor, pressColor);
        addHoverEffect(attachButton, defaultColor, hoverColor, pressColor);
        addHoverEffect(imageButton, defaultColor, hoverColor, pressColor);

        setVisible(true);
    }

    private void styleFlatButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);  // <- BẮT BUỘC PHẢI LÀ true để hiệu ứng màu nền hoạt động
        button.setBackground(new Color(44, 47, 51));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(36, 36));
    }
    

    private void addHoverEffect(JButton button, Color defaultColor, Color hoverColor, Color pressColor) {
        button.setBackground(defaultColor);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(hoverColor); }
            public void mouseExited(MouseEvent e) { button.setBackground(defaultColor); }
            public void mousePressed(MouseEvent e) { button.setBackground(pressColor); }
            public void mouseReleased(MouseEvent e) {
                if (button.contains(e.getPoint())) {
                    button.setBackground(hoverColor);
                } else {
                    button.setBackground(defaultColor);
                }
            }
        });
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            chatArea.append("Tôi: " + message + "\n");
            inputField.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InternalChatUI::new);
    }
}
