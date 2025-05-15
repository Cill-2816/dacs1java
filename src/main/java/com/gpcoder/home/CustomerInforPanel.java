package com.gpcoder.home;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

public class CustomerInforPanel extends JPanel {

    private JTextField nameField;
    private JLabel guestLabel;
    private DatePicker datePicker;
    private TimePicker timePicker;

    public CustomerInforPanel(CardLayout cardLayout, JPanel parentPanel) {
        setLayout(new BorderLayout());
        setBackground(new Color(30, 32, 34));
        setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel title = new JLabel("Customer Information", SwingConstants.LEFT);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        // --- FORM ---
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(getBackground());

        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(createInputField("Name", nameField = new JTextField("Kamalesh Roy")));
        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(createGuestSelector());
        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(createDatePickerField("Date"));
        formPanel.add(Box.createVerticalStrut(16));
        formPanel.add(createTimePickerField("Time"));

        add(formPanel, BorderLayout.CENTER);

        // --- BUTTONS ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(getBackground());
        buttonPanel.setBorder(new EmptyBorder(30, 0, 0, 0));

        RoundedButton backButton = new RoundedButton("Back", 20);
        backButton.setPreferredSize(new Dimension(120, 42));
        backButton.setBackground(getBackground());
        backButton.setForeground(Color.WHITE);
        backButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> cardLayout.show(parentPanel, "type"));

        RoundedButton nextButton = new RoundedButton("Next", 20);
        nextButton.setPreferredSize(new Dimension(120, 42));
        nextButton.setBackground(new Color(255, 87, 34));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFont(new Font("Arial", Font.BOLD, 16));
        nextButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        nextButton.addActionListener(e -> cardLayout.show(parentPanel, "payment"));

        buttonPanel.add(backButton);
        buttonPanel.add(nextButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Field: Name
    private JPanel createInputField(String label, JTextField field) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(getBackground());
        container.setOpaque(true);
        container.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPanel fieldPanel = new RoundedPanel(18);
        fieldPanel.setLayout(new BorderLayout());
        fieldPanel.setBackground(new Color(44, 47, 51));
        fieldPanel.setBorder(new EmptyBorder(10, 16, 10, 16));
        fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.setMaximumSize(new Dimension(320, 48));

        field.setBorder(null);
        field.setBackground(new Color(44, 47, 51));
        field.setForeground(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 16));
        field.setCaretColor(Color.WHITE);
        field.setPreferredSize(new Dimension(220, 26));
        field.setMaximumSize(new Dimension(220, 26));

        fieldPanel.add(field, BorderLayout.CENTER);

        container.add(lbl);
        container.add(Box.createVerticalStrut(7));
        container.add(fieldPanel);
        container.setMaximumSize(new Dimension(350, 60));
        return container;
    }

    // Field: DatePicker với custom icon
    private JPanel createDatePickerField(String label) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(getBackground());
        container.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPanel fieldPanel = new RoundedPanel(18);
        fieldPanel.setLayout(new BorderLayout());
        fieldPanel.setBackground(new Color(44, 47, 51));
        fieldPanel.setBorder(new EmptyBorder(6, 10, 6, 10));
        fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.setMaximumSize(new Dimension(320, 48));

        datePicker = new DatePicker();
        datePicker.setBackground(new Color(44, 47, 51));
        datePicker.getComponentDateTextField().setBackground(new Color(44, 47, 51));
        datePicker.getComponentDateTextField().setForeground(Color.WHITE);
        datePicker.getComponentDateTextField().setFont(new Font("Arial", Font.PLAIN, 16));
        datePicker.getComponentDateTextField().setCaretColor(Color.WHITE);
        datePicker.getComponentDateTextField().setBorder(null);
        datePicker.setPreferredSize(new Dimension(150, 28));
        datePicker.setMaximumSize(new Dimension(200, 28));

        // ---- Set custom icon cho nút lịch ----
        try {
            // Thay đường dẫn này thành đúng tên icon của bạn
            ImageIcon calendarIcon = new ImageIcon("image/calendar.png");
            Image imgCal = calendarIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            calendarIcon = new ImageIcon(imgCal);

            JButton calendarButton = datePicker.getComponentToggleCalendarButton();
            calendarButton.setIcon(calendarIcon);
            calendarButton.setText(""); // Ẩn text "..."
            calendarButton.setBorderPainted(false);
            calendarButton.setFocusPainted(false);
            calendarButton.setContentAreaFilled(false);
        } catch (Exception e) {
            // Nếu không tìm thấy icon sẽ không bị lỗi giao diện
            System.out.println("Không tìm thấy icon calendar.png");
        }
        // ---- End custom icon ----

        fieldPanel.add(datePicker, BorderLayout.CENTER);

        container.add(lbl);
        container.add(Box.createVerticalStrut(7));
        container.add(fieldPanel);
        container.setMaximumSize(new Dimension(350, 60));
        return container;
    }

    // Field: TimePicker với custom icon
    private JPanel createTimePickerField(String label) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(getBackground());
        container.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel(label);
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPanel fieldPanel = new RoundedPanel(18);
        fieldPanel.setLayout(new BorderLayout());
        fieldPanel.setBackground(new Color(44, 47, 51));
        fieldPanel.setBorder(new EmptyBorder(6, 10, 6, 10));
        fieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        fieldPanel.setMaximumSize(new Dimension(320, 48));

        timePicker = new TimePicker();
        timePicker.setBackground(new Color(44, 47, 51));
        timePicker.getComponentTimeTextField().setBackground(new Color(44, 47, 51));
        timePicker.getComponentTimeTextField().setForeground(Color.WHITE);
        timePicker.getComponentTimeTextField().setFont(new Font("Arial", Font.PLAIN, 16));
        timePicker.getComponentTimeTextField().setCaretColor(Color.WHITE);
        timePicker.getComponentTimeTextField().setBorder(null);
        timePicker.setPreferredSize(new Dimension(100, 28));
        timePicker.setMaximumSize(new Dimension(200, 28));

        // ---- Set custom icon cho nút giờ ----
        try {
            // Thay đường dẫn này thành đúng tên icon của bạn
            ImageIcon clockIcon = new ImageIcon("image/clock.png");
            Image imgClock = clockIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            clockIcon = new ImageIcon(imgClock);

            JButton timeButton = timePicker.getComponentToggleTimeMenuButton();
            timeButton.setIcon(clockIcon);
            timeButton.setText(""); // Ẩn text "▼"
            timeButton.setBorderPainted(false);
            timeButton.setFocusPainted(false);
            timeButton.setContentAreaFilled(false);
        } catch (Exception e) {
            System.out.println("Không tìm thấy icon clock.png");
        }
        // ---- End custom icon ----

        fieldPanel.add(timePicker, BorderLayout.CENTER);

        container.add(lbl);
        container.add(Box.createVerticalStrut(7));
        container.add(fieldPanel);
        container.setMaximumSize(new Dimension(350, 60));
        return container;
    }

    // Guest selector
    private JPanel createGuestSelector() {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(getBackground());
        container.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel lbl = new JLabel("Number of Guest");
        lbl.setForeground(Color.LIGHT_GRAY);
        lbl.setFont(new Font("Arial", Font.PLAIN, 15));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        RoundedPanel selector = new RoundedPanel(18);
        selector.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 6));
        selector.setBackground(new Color(44, 47, 51));

        JButton minus = createCircleButton("-");
        JButton plus = createCircleButton("+");
        guestLabel = new JLabel("4");
        guestLabel.setForeground(Color.WHITE);
        guestLabel.setFont(new Font("Arial", Font.BOLD, 18));

        minus.addActionListener(e -> {
            int val = Integer.parseInt(guestLabel.getText());
            if (val > 1) guestLabel.setText(String.valueOf(val - 1));
        });
        plus.addActionListener(e -> {
            int val = Integer.parseInt(guestLabel.getText());
            guestLabel.setText(String.valueOf(val + 1));
        });

        selector.add(minus);
        selector.add(guestLabel);
        selector.add(plus);

        container.add(lbl);
        container.add(Box.createVerticalStrut(7));
        container.add(selector);
        return container;
    }

    private JButton createCircleButton(String text) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(40, 40));
        btn.setFont(new Font("Arial", Font.BOLD, 20));
        btn.setBackground(new Color(255, 87, 34));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }
}
