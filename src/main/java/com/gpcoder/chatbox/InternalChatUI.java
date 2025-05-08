package com.gpcoder.chatbox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class InternalChatUI extends JFrame {
    private JPanel chatBody;
    private JScrollPane chatScrollPane;
    private JTextField inputField;
    private JList<User> userList;
    private JButton sendButton;
    private ChatHeaderPanel chatHeader;
    private String lastSender = "";

    public InternalChatUI() {
        setTitle("Internal Chat");
        setSize(1440, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bgColor = new Color(44, 47, 51);
        Color panelColor = bgColor;
        Color textColor = Color.WHITE;

        setLayout(new BorderLayout());

        DefaultListModel<User> model = new DefaultListModel<>();
        model.addElement(new User("Alice", "image/avata.png"));
        model.addElement(new User("Bob", "image/avata.png"));
        model.addElement(new User("Charlie", "image/avata.png"));

        userList = new JList<>(model);
        userList.setCellRenderer(new UserCellRenderer());
        userList.setFixedCellHeight(48);
        userList.setSelectionBackground(new Color(70, 130, 180));
        userList.setBackground(new Color(44, 47, 51));

        JScrollPane userScrollPane = new JScrollPane(userList);
        userScrollPane.setPreferredSize(new Dimension(180, 0));
        add(userScrollPane, BorderLayout.WEST);

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.setBackground(bgColor);

        chatHeader = new ChatHeaderPanel();
        chatPanel.add(chatHeader, BorderLayout.NORTH);

        chatBody = new JPanel(new GridBagLayout());
        chatBody.setBackground(bgColor);
        chatScrollPane = new JScrollPane(chatBody);
        chatScrollPane.setBorder(null);
        chatScrollPane.getVerticalScrollBar().setUnitIncrement(16);
        chatPanel.add(chatScrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(panelColor);
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        ImageIcon attachIcon = new ImageIcon(new ImageIcon("image/attach.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        ImageIcon imageIcon = new ImageIcon(new ImageIcon("image/picture.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        JButton attachButton = new JButton(attachIcon);
        JButton imageButton = new JButton(imageIcon);
        styleFlatButton(attachButton);
        styleFlatButton(imageButton);
        attachButton.setToolTipText("Attach a file");
        imageButton.setToolTipText("Send an image");

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        leftPanel.setBackground(panelColor);
        leftPanel.add(attachButton);
        leftPanel.add(imageButton);

        inputField = new JTextField();
        inputField.setBackground(panelColor);
        inputField.setForeground(textColor);
        inputField.setCaretColor(textColor);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        inputField.setToolTipText("Send message (Enter to send)");
        inputField.addActionListener(e -> sendMessage());

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(panelColor);
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 63, 65), 1, true));
        centerPanel.add(inputField, BorderLayout.CENTER);

        ImageIcon sendIcon = new ImageIcon(new ImageIcon("image/send.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        sendButton = new JButton(sendIcon);
        styleFlatButton(sendButton);
        sendButton.setToolTipText("Send message (Enter to send)");
        sendButton.addActionListener(e -> sendMessage());

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setBackground(panelColor);
        rightPanel.add(sendButton);

        inputPanel.add(leftPanel, BorderLayout.WEST);
        inputPanel.add(centerPanel, BorderLayout.CENTER);
        inputPanel.add(rightPanel, BorderLayout.EAST);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);

        add(chatPanel, BorderLayout.CENTER);

        Color defaultColor = panelColor;
        Color hoverColor = new Color(60, 63, 65);
        Color pressColor = new Color(84, 88, 95);
        addHoverEffect(sendButton, defaultColor, hoverColor, pressColor);
        addHoverEffect(attachButton, defaultColor, hoverColor, pressColor);
        addHoverEffect(imageButton, defaultColor, hoverColor, pressColor);

        userList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                User selected = userList.getSelectedValue();
                if (selected != null) {
                    chatHeader.setUser(selected, true);
                }
            }
        });

        setVisible(true);
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            String sender = "Me";
            boolean isMine = true;
            boolean isContinuation = sender.equals(lastSender);

            BubblePanel bubble = new BubblePanel(sender, message, isMine, isContinuation);

            for (Component comp : chatBody.getComponents()) {
                if ("spacer".equals(comp.getName())) {
                    chatBody.remove(comp);
                    break;
                }
            }

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = chatBody.getComponentCount();
            gbc.weightx = 1.0;
            gbc.anchor = isMine ? GridBagConstraints.EAST : GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            chatBody.add(bubble, gbc);

            GridBagConstraints spacer = new GridBagConstraints();
            spacer.gridx = 0;
            spacer.gridy = chatBody.getComponentCount();
            spacer.weighty = 1.0;
            spacer.fill = GridBagConstraints.VERTICAL;

            JPanel empty = new JPanel();
            empty.setOpaque(false);
            empty.setName("spacer");
            chatBody.add(empty, spacer);

            chatBody.revalidate();
            JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
            vertical.setValue(vertical.getMaximum());

            inputField.setText("");
            lastSender = sender;
        }
    }
    
    // CUSTOM NHẬN TIN NHẮN
    private void receiveMessage(String sender, String message) {
        boolean isMine = false;
        boolean isContinuation = sender.equals(lastSender);

        BubblePanel bubble = new BubblePanel(sender, message, isMine, isContinuation);

        for (Component comp : chatBody.getComponents()) {
            if ("spacer".equals(comp.getName())) {
                chatBody.remove(comp);
                break;
            }
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = chatBody.getComponentCount();
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        chatBody.add(bubble, gbc);

        GridBagConstraints spacer = new GridBagConstraints();
        spacer.gridx = 0;
        spacer.gridy = chatBody.getComponentCount();
        spacer.weighty = 1.0;
        spacer.fill = GridBagConstraints.VERTICAL;

        JPanel empty = new JPanel();
        empty.setOpaque(false);
        empty.setName("spacer");
        chatBody.add(empty, spacer);

        chatBody.revalidate();
        JScrollBar vertical = chatScrollPane.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());

        lastSender = sender;
    }

    private void styleFlatButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setBackground(new Color(44, 47, 51));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(36, 36));
        button.setOpaque(true);
    }

    private void addHoverEffect(JButton button, Color defaultColor, Color hoverColor, Color pressColor) {
        button.setBackground(defaultColor);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(hoverColor); }
            public void mouseExited(MouseEvent e) { button.setBackground(defaultColor); }
            public void mousePressed(MouseEvent e) { button.setBackground(pressColor); }
            public void mouseReleased(MouseEvent e) {
                button.setBackground(button.contains(e.getPoint()) ? hoverColor : defaultColor);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(InternalChatUI::new);
    }
}