package com.gpcoder.staffpanel;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ActionRenderer extends JPanel implements TableCellRenderer {
    public ActionRenderer() {
        setLayout(new GridBagLayout());
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        this.removeAll();

        ImageIcon editIcon = new ImageIcon(new ImageIcon("image/edit.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        ImageIcon deleteIcon = new ImageIcon(new ImageIcon("image/delete.png").getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH));
        JButton editButton = new JButton(editIcon);
        JButton deleteButton = new JButton(deleteIcon);

        styleButton(editButton);
        styleButton(deleteButton);

        JPanel innerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        innerPanel.setOpaque(false);
        innerPanel.add(editButton);
        innerPanel.add(deleteButton);

        this.add(innerPanel);
        this.setBackground(table.getBackground());

        return this;
    }

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(24, 24));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
    }
}