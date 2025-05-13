package com.gpcoder.staffpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class StaffPanel extends JPanel {
    public StaffPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(24, 26, 27));

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(24, 26, 27));
        JLabel title = new JLabel("Staff Management");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        JButton addButton = new JButton("+ Add Staff");
        addButton.setBackground(new Color(255, 87, 34));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        header.add(addButton, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(new Color(24, 26, 27));

        JTextField searchField = new JTextField("Search by name, phone...");
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBackground(new Color(44, 47, 51));
        searchField.setForeground(Color.GRAY);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JComboBox<String> roleFilter = new JComboBox<>(new String[]{"All Roles", "Chef", "Cashier", "Waiter", "Manager"});
        roleFilter.setPreferredSize(new Dimension(150, 35));
        roleFilter.setBackground(new Color(44, 47, 51));
        roleFilter.setForeground(Color.WHITE);
        roleFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"All Status", "Active", "Inactive"});
        statusFilter.setPreferredSize(new Dimension(150, 35));
        statusFilter.setBackground(new Color(44, 47, 51));
        statusFilter.setForeground(Color.WHITE);
        statusFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        filterPanel.add(searchField);
        filterPanel.add(roleFilter);
        filterPanel.add(statusFilter);

        // Table
        String[] columns = {"StafF ID", "First Name", "Last Name", "Gender", "Phone Number", "Address", "Position", "Actions"};

        Object[][] data = {
            {"001", "Trần", "Văn A", "Nam", "0909899414", "Đà Nẵng", "Chef", null},
            {"002", "Nguyễn", "Thị B", "Nữ", "0938221335", "Huế", "Cashier", null},
            {"003", "Lê", "Văn C", "Nam", "0123988773", "Hà Nội", "Waiter", null},
            {"004", "Phạm", "Thị D", "Nữ", "0987112551", "Sài Gòn", "Manager", null}
        };

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(50);
        table.setBackground(new Color(36, 40, 45));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(50, 54, 60));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(36, 40, 45));
        table.getTableHeader().setForeground(Color.LIGHT_GRAY);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        // Set custom renderer for "Actions" column
        table.getColumn("Actions").setCellRenderer(new ActionRenderer());

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(36, 40, 45));
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(new Color(24, 26, 27));
        center.setBorder(new EmptyBorder(10, 0, 10, 0));
        center.add(filterPanel, BorderLayout.NORTH);
        center.add(scrollPane, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }
}
