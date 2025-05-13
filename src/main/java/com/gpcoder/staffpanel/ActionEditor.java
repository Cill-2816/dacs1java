package com.gpcoder.staffpanel;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.util.Set;

import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ActionEditor extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    private final JPanel panel;
    private final JButton editButton;
    private final JButton deleteButton;
    private final Set<Integer> editableRows;

    public ActionEditor(JTable table, Set<Integer> editableRows) {
        this.editableRows = editableRows;

        panel = new JPanel(new GridBagLayout());
        panel.setOpaque(true);

        ImageIcon editIcon = new ImageIcon("image/edit.png");
        ImageIcon deleteIcon = new ImageIcon("image/delete.png");

        Image editImg = editIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        Image deleteImg = deleteIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);

        editButton = new JButton(new ImageIcon(editImg));
        deleteButton = new JButton(new ImageIcon(deleteImg));

        styleButton(editButton);
        styleButton(deleteButton);

        editButton.addActionListener(e -> {
            int viewRow = table.getEditingRow();
            if (viewRow < 0) return;                        // an toàn

            int modelRow = table.convertRowIndexToModel(viewRow);

            // add() trả true nếu phần tử CHƯA có trong Set ⇒ chỉ hiện thông báo 1 lần/row
            if (editableRows.add(modelRow)) {
                JOptionPane.showMessageDialog(table, "Bạn có thể sửa dòng này");
            }

            // 1) thoát editor hiện tại
            fireEditingStopped();

            // 2) Mở ô đầu dòng cho nhập SAU khi editor đã dừng hẳn
            SwingUtilities.invokeLater(() -> table.editCellAt(viewRow, 0));
        });


        deleteButton.addActionListener(e -> {
            int row = table.getEditingRow();
            String name = table.getValueAt(row, 1) + " " + table.getValueAt(row, 2);
            int confirm = JOptionPane.showConfirmDialog(table,
                    "Bạn có chắc muốn xóa nhân viên \"" + name + "\"?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                ((DefaultTableModel) table.getModel()).removeRow(row);
            }
            fireEditingStopped();
        });

        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        innerPanel.setOpaque(false);
        innerPanel.add(editButton);
        innerPanel.add(deleteButton);
        panel.add(innerPanel);
    }

    private void styleButton(JButton b) {
        b.setPreferredSize(new Dimension(24, 24));
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setFocusPainted(false);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        panel.setBackground(table.getBackground());
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus, int row, int column) {
        panel.setBackground(table.getBackground());
        return panel;
    }
}
