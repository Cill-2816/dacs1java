package com.gpcoder.staffpanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ActionRenderer extends JPanel implements TableCellRenderer {

    public ActionRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.removeAll();

        JButton editButton = new JButton(new ImageIcon("image/edit.png"));
        JButton deleteButton = new JButton(new ImageIcon("image/delete.png"));

        editButton.setBorderPainted(false);
        editButton.setContentAreaFilled(false);
        editButton.setFocusPainted(false);

        deleteButton.setBorderPainted(false);
        deleteButton.setContentAreaFilled(false);
        deleteButton.setFocusPainted(false);

        this.add(editButton);
        this.add(deleteButton);

        setBackground(new Color(36, 40, 45)); // same as table background
        return this;
    }
}
