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
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.gpcoder.model.Historychat;

public class InternalChatUI extends JFrame {
    private JPanel chatBody;
    private JScrollPane chatScrollPane;
    private JTextField inputField;
    private JList<User> userList;
    private JButton sendButton;
    private ChatHeaderPanel chatHeader;
    private String lastSender = "";
    private Socket socket;
    private ObjectOutputStream outStream;
    private ObjectInputStream inStream;
    private String currentuser;
    private File selectedFile;

    public InternalChatUI() {
        setTitle("Internal Chat");
        setSize(1440, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        Color bgColor = new Color(44, 47, 51);
        Color panelColor = bgColor;
        Color textColor = Color.WHITE;

        setLayout(new BorderLayout());

        //set nguoi dung hien tai
        this.currentuser = "Anhtdd";

        DefaultListModel<User> model = new DefaultListModel<>();
        model.addElement(new User("Chauttn","Chau", "image/avata.png"));
        model.addElement(new User("Anhtdd","Anh", "image/avata.png"));
        model.addElement(new User("Khaipm","Khai", "image/avata.png"));

        for (int i = 0; i < model.getSize(); i++) {
            User user = model.getElementAt(i);
            if (user.getUsername().equals(currentuser)) {
                model.removeElementAt(i);
                break; 
            }
        }

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

        attachButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedFile = chooser.getSelectedFile();
                sendButton.setEnabled(true);
                inputField.setText(selectedFile.getName());
            }
        });

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
        inputField.addActionListener(e -> {
            try {
                String text_message = inputField.getText().trim();
                sendMessage(text_message, LocalDateTime.now());
                outStream.writeObject("NEW_MESSAGE:" + currentuser + ":" + userList.getSelectedValue().getUsername() + ":" + text_message);
                outStream.flush();
                System.out.println("NEW_MESSAGE:" + currentuser + ":" + userList.getSelectedValue().getUsername() + ":" + text_message);
            } catch (IOException ex) {
            }
            
        });

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(panelColor);
        centerPanel.setBorder(BorderFactory.createLineBorder(new Color(60, 63, 65), 1, true));
        centerPanel.add(inputField, BorderLayout.CENTER);

        ImageIcon sendIcon = new ImageIcon(new ImageIcon("image/send.png").getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH));
        sendButton = new JButton(sendIcon);
        styleFlatButton(sendButton);
        sendButton.setToolTipText("Send message (Enter to send)");
        sendButton.addActionListener(e -> {
            try {
                String text_message = inputField.getText().trim();
                if (selectedFile == null) {
                    sendMessage(text_message, LocalDateTime.now());
                    outStream.writeObject("NEW_MESSAGE:" + currentuser + ":" + userList.getSelectedValue().getUsername() + ":" + text_message);
                    outStream.flush();
                }
                else {
                    sendFile(selectedFile, LocalDateTime.now());
                    outStream.writeObject("NEW_FILE:" + currentuser + ":" + userList.getSelectedValue().getUsername() + ":" + "sent_file/"+selectedFile.getName());
                    outStream.flush();
                }
            } catch (IOException ex) {
            }
            
        });

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
                    chatBody.removeAll();

                    requestHistory(currentuser,selected.getUsername());
                }
            }
        });

        setVisible(true);

        new Thread(this::runNetworking).start();
    }

    private void requestHistory(String username, String username2) {
        try {
            if (socket != null && socket.isConnected() && !socket.isOutputShutdown()) {
                outStream.writeObject("GET_HISTORY:" + username + ":" + username2);
                outStream.flush();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi gửi yêu cầu lịch sử: " + e.getMessage());
        }
    }


    private void runNetworking() {
    try {
        this.socket = new Socket("localhost", 12345);
        this.outStream = new ObjectOutputStream(socket.getOutputStream());
        this.inStream = new ObjectInputStream(socket.getInputStream());
        
        // Nhận lịch sử
        while (true) {
            Object obj = inStream.readObject();

            if (obj != null && obj instanceof Historychat) {
                Historychat chat = (Historychat) obj;
                SwingUtilities.invokeLater(() -> 
                    receiveMessage(chat.getSent_id(), chat.getMessage(), chat.getSent_time())
                );
            } else if (obj != null && obj instanceof List<?>) {
                List<Historychat> list = (List<Historychat>) obj;
                // Clear chatBody trước khi hiển thị lịch sử mới
                SwingUtilities.invokeLater(() -> {
                    chatBody.removeAll();
                    for (Historychat o : list) {
                        if (o instanceof Historychat) {
                            if (o.getSent_id().equals(currentuser)) {
                                if (o.getMessage_type().equals("text")) {
                                    sendMessage(o.getMessage(), o.getSent_time());
                                }
                                else {
                                    File file = new File(o.getMessage());
                                    sendFile(file, o.getSent_time());
                                }
                            } else {
                                if (o.getMessage_type().equals("text")) {
                                    receiveMessage(o.getSent_id(), o.getMessage(), o.getSent_time());
                                }
                                else {
                                    File file = new File(o.getMessage());
                                    receiveFile(o.getSent_id(),file, o.getSent_time());
                                }
                                
                            }
                        }
                    }
                    chatBody.revalidate();
                    chatBody.repaint();
                });
            }
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message, LocalDateTime timesent) {
        if (!message.isEmpty()) {
            String sender = "Me";
            boolean isMine = true;
            boolean isContinuation = sender.equals(lastSender);

            BubblePanel bubble = new BubblePanel(sender, message, isMine, isContinuation, timesent);

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

    private void sendFile(File file, LocalDateTime timesent) {

        if (!file.getName().isEmpty()) {
            String sender = "Me";
            boolean isMine = true;
            boolean isContinuation = sender.equals(lastSender);

            FileBubblePanel bubble = new FileBubblePanel(file.getName(), file.length(), file, isMine, isContinuation, LocalDateTime.now());

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
    private void receiveMessage(String sender, String message, LocalDateTime timesent) {
        boolean isMine = false;
        boolean isContinuation = sender.equals(lastSender);

        BubblePanel bubble = new BubblePanel(sender, message, isMine, isContinuation, timesent);

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

    private void receiveFile(String sender, File file, LocalDateTime timesent) {
        boolean isMine = false;
        boolean isContinuation = sender.equals(lastSender);

        FileBubblePanel bubble = new FileBubblePanel(file.getName(), file.length(), file, isMine, isContinuation, LocalDateTime.now());

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