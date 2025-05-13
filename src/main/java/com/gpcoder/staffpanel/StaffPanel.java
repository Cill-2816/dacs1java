package com.gpcoder.staffpanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
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
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);

        JButton addButton = new JButton("+ Add Staff");
        addButton.setBackground(new Color(255, 87, 34));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        header.add(addButton, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Filter
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        filterPanel.setBackground(new Color(24, 26, 27));

        JTextField searchField = new JTextField("Search by name, phone...");
        searchField.setPreferredSize(new Dimension(250, 35));
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBackground(new Color(44, 47, 51));
        searchField.setForeground(Color.GRAY);
        searchField.setCaretColor(Color.WHITE);
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JComboBox<String> roleFilter = new JComboBox<>(new String[]{"All Roles", "Chef", "Cashier", "Waiter", "Manager"});
        JComboBox<String> statusFilter = new JComboBox<>(new String[]{"All Status", "Active", "Inactive"});
        for (JComboBox<String> box : new JComboBox[]{roleFilter, statusFilter}) {
            box.setPreferredSize(new Dimension(150, 35));
            box.setBackground(new Color(44, 47, 51));
            box.setForeground(Color.WHITE);
            box.setFont(new Font("Arial", Font.PLAIN, 14));
        }

        filterPanel.add(searchField);
        filterPanel.add(roleFilter);
        filterPanel.add(statusFilter);

        // Table data
        String[] columns = {"Staff ID", "First Name", "Last Name", "Gender", "Phone Number", "Address", "Position", "Status", "Actions"};
        Object[][] data = {
                {"001", "Trần", "Văn A", "Nam", "0909899414", "Đà Nẵng", "Chef", "Active", null},
                {"002", "Nguyễn", "Thị B", "Nữ", "0938221335", "Huế", "Cashier", "Active", null},
                {"003", "Lê", "Văn C", "Nam", "0123988773", "Hà Nội", "Waiter", "Inactive", null},
                {"004", "Phạm", "Thị D", "Nữ", "0987112551", "Sài Gòn", "Manager", "Active", null}
        };

        Set<Integer> editableRows = new HashSet<>();

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
            // cột Actions (8) luôn editable;
            // các cột khác chỉ editable khi đã bấm Edit
            return column == 8 || editableRows.contains(row);
        }
        };

        JTable table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(50);
        table.setBackground(new Color(36, 40, 45));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(50, 54, 60));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(36, 40, 45));
        table.getTableHeader().setForeground(Color.LIGHT_GRAY);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);

        table.getColumn("Actions").setCellEditor(new ActionEditor(table, editableRows));
        table.getColumn("Actions").setCellRenderer(new ActionRenderer()); // Chỉ dùng renderer cho hiển thị

        // --- MouseListener ---
        table.addMouseListener(new MouseAdapter() {
        @Override
            public void mousePressed(MouseEvent e) {
            Point p = e.getPoint();
            int viewRow    = table.rowAtPoint(p);
            int viewColumn = table.columnAtPoint(p);

            // chuyển sang chỉ số model tránh lỗi khi người dùng kéo–thả cột
            int modelColumn = table.convertColumnIndexToModel(viewColumn);

            if (modelColumn == 8) {          // cột Actions
                table.editCellAt(viewRow, viewColumn);     // khởi tạo editor
                Component editor = table.getEditorComponent();
                if (editor != null) {
                    MouseEvent evt = SwingUtilities.convertMouseEvent(table, e, editor);
                    editor.dispatchEvent(evt);             // chuyển sự kiện click xuống nút
                }
            }
        }
});


        // Hiện thông báo sau khi Enter để lưu
        // 1️⃣  Khai báo một biến cục bộ để giữ giá trị cũ
final Object[] oldValue = {null};
final int[]    oldRow   = {-1};
final int[]    oldCol   = {-1};

// 2️⃣  Lấy editor mặc định (JTextField) cho mọi ô kiểu Object
DefaultCellEditor textEditor =
        (DefaultCellEditor) table.getDefaultEditor(Object.class);

// 3️⃣  Ghi đè getTableCellEditorComponent để nhớ giá trị gốc
textEditor = new DefaultCellEditor(new javax.swing.JTextField()) {
    @Override
    public Component getTableCellEditorComponent(JTable tbl, Object value,
            boolean isSelected, int row, int column) {
        oldValue[0] = value;                 // ⭐ giá trị trước khi sửa
        oldRow[0]   = row;
        oldCol[0]   = column;
        return super.getTableCellEditorComponent(tbl, value, isSelected, row, column);
    }
};

// 4️⃣  Khi editor dừng, so sánh giá trị mới–cũ
textEditor.addCellEditorListener(new javax.swing.event.CellEditorListener() {
    @Override
    public void editingStopped(javax.swing.event.ChangeEvent e) {
        // Bỏ qua cột Actions (8) hoặc nếu hàng chưa được cấp quyền sửa
        if (oldCol[0] == 8 || !editableRows.contains(table.convertRowIndexToModel(oldRow[0]))) {
            return;
        }

        Object newValue = model.getValueAt(oldRow[0], oldCol[0]);
        if (!java.util.Objects.equals(oldValue[0], newValue)) {        // ✅ thực sự đổi
            JOptionPane.showMessageDialog(table,
                "Sửa thành công dòng " + (table.convertRowIndexToView(oldRow[0]) + 1));
        }
    }
    @Override public void editingCanceled(javax.swing.event.ChangeEvent e) { }
});

table.setDefaultEditor(Object.class, textEditor);



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
