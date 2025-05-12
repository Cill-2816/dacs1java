// Improved AccountingPanel.java
package com.gpcoder.accounting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.ui.RectangleEdge;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class AccountingPanel extends JPanel {
    private JTable orderTable;
    private DefaultTableModel orderModel;

    public AccountingPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(18, 20, 24));

        // Header
        JLabel title = new JLabel("Accounting");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(getBackground());
        header.setBorder(new EmptyBorder(10, 10, 0, 10));
        header.add(title, BorderLayout.WEST);
        add(header, BorderLayout.NORTH);

        // Summary cards
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setBackground(getBackground());
        summaryPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        summaryPanel.add(createStatCard("Total Revenue", "$25,000,000"));
        summaryPanel.add(createStatCard("Orders", "150"));
        summaryPanel.add(createStatCard("Cancellation Rate", "3%"));

        // Charts
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        chartsPanel.setBackground(getBackground());
        chartsPanel.setBorder(new EmptyBorder(10, 10, 0, 10));
        chartsPanel.add(createRevenueChart());
        chartsPanel.add(createPieChart());

        // Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(getBackground());
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel tableTitle = new JLabel("Order List");
        tableTitle.setFont(new Font("Arial", Font.BOLD, 18));
        tableTitle.setForeground(Color.WHITE);
        tablePanel.add(tableTitle, BorderLayout.NORTH);

        String[] columns = {"#", "Date Time", "Total", "Status"};
        Object[][] data = {
            {"1024", "11 Apr, 2022 15:00", "$25,000,000", "Paid"},
            {"1023", "11 Apr, 2022 15:00", "$15,400,000", "Cancelled"},
            {"1022", "11 Apr, 2022 15:00", "$50,000,000", "Pending"},
            {"1021", "11 Apr, 2022 15:00", "$27,000,000", "Paid"},
            {"1020", "11 Apr, 2022 14:00", "$22,000,000", "Paid"},
            {"1019", "11 Apr, 2022 13:30", "$10,000,000", "Pending"}
        };

        orderModel = new DefaultTableModel(data, columns);
        orderTable = new JTable(orderModel);
        orderTable.setFont(new Font("Arial", Font.PLAIN, 13));
        orderTable.setRowHeight(30);
        orderTable.setBackground(new Color(28, 30, 35));
        orderTable.setForeground(Color.WHITE);
        orderTable.setGridColor(new Color(60, 64, 70));
        orderTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        orderTable.getTableHeader().setForeground(Color.WHITE);
        orderTable.getTableHeader().setBackground(new Color(28, 30, 35));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        orderTable.setDefaultRenderer(Object.class, centerRenderer);

        JScrollPane scrollPane = new JScrollPane(orderTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(orderTable.getBackground());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        scrollPane.getVerticalScrollBar().setUI(new DarkScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new DarkScrollBarUI()); // nếu muốn dùng ngang
        scrollPane.setCorner(JScrollPane.LOWER_RIGHT_CORNER, new JPanel() {{
            setOpaque(false);           // lấp góc trống, cùng màu nền
        }});
        SwingUtilities.invokeLater(() -> { scrollPane.getVerticalScrollBar().setValue(0);  // Đưa về đầu
        });

        // Center area
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(getBackground());
        center.add(summaryPanel);
        center.add(Box.createVerticalStrut(20));
        center.add(chartsPanel);
        center.add(Box.createVerticalStrut(20));
        center.add(tablePanel);

        add(center, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String title, String value) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(34, 36, 40));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 64, 70), 1),
            new EmptyBorder(15, 20, 15, 20)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        titleLabel.setForeground(Color.LIGHT_GRAY);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 22));
        valueLabel.setForeground(Color.WHITE);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(valueLabel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createRevenueChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 1; i <= 12; i++) {
            dataset.addValue((int) (Math.random() * 90 + 10), "Revenue", String.valueOf(i));
        }

        JFreeChart chart = ChartFactory.createBarChart("Revenue", "Month", "Million VND", dataset);
        chart.setBackgroundPaint(getBackground());
        chart.getTitle().setFont(new Font("Arial", Font.BOLD, 20));  // hoặc "Poppins", "Segoe UI"...
        chart.getTitle().setPaint(Color.WHITE);


        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(28, 30, 35));
        plot.setDomainGridlinePaint(Color.DARK_GRAY);
        plot.setRangeGridlinePaint(Color.DARK_GRAY);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new Color(0, 191, 165));
        renderer.setShadowVisible(false);

        plot.getDomainAxis().setTickLabelPaint(Color.LIGHT_GRAY);
        plot.getRangeAxis().setTickLabelPaint(Color.LIGHT_GRAY);
        chart.removeLegend();

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground((Color) plot.getBackgroundPaint());
        chartPanel.setBorder(BorderFactory.createEmptyBorder());
        return chartPanel;
    }

        private JPanel createPieChart() {
    // Dataset
    DefaultPieDataset dataset = new DefaultPieDataset();
    dataset.setValue("Beef Noodle Soup", 40);
    dataset.setValue("Banh Mi", 25);
    dataset.setValue("Coffee", 20);
    dataset.setValue("Milk Tea", 15);

    // Tạo chart KHÔNG có tiêu đề
    JFreeChart chart = ChartFactory.createPieChart(null, dataset, true, false, false);
    chart.setBackgroundPaint(new Color(28, 30, 35));

    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setBackgroundPaint(new Color(28, 30, 35));
    plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{2}"));
    plot.setLabelFont(new Font("Arial", Font.BOLD, 13));
    plot.setLabelPaint(Color.BLACK);
    plot.setLabelBackgroundPaint(Color.WHITE);
    plot.setLabelOutlinePaint(Color.GRAY);
    plot.setLabelShadowPaint(null);
    plot.setOutlineVisible(false);
    plot.setShadowPaint(null);
    plot.setSimpleLabels(true);

    LegendTitle legend = chart.getLegend();
    if (legend != null) {
        legend.setItemFont(new Font("Arial", Font.PLAIN, 12));
        legend.setItemPaint(Color.LIGHT_GRAY);
        legend.setFrame(BlockBorder.NONE);
        legend.setBackgroundPaint(new Color(28, 30, 35));
        legend.setPosition(RectangleEdge.BOTTOM);
    }

    // ChartPanel chỉ chứa biểu đồ
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setBackground(new Color(28, 30, 35));
    chartPanel.setPreferredSize(new Dimension(350, 260));
    chartPanel.setBorder(BorderFactory.createEmptyBorder());

    // Tiêu đề ở phía trên – nằm ngoài biểu đồ
    JLabel titleLabel = new JLabel("Top Selling Items", JLabel.CENTER);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setBorder(new EmptyBorder(0, 0, 10, 0));  // khoảng cách dưới

    // Panel bọc bên ngoài
    JPanel wrapper = new JPanel();
    wrapper.setLayout(new BorderLayout());
    wrapper.setBackground(new Color(18, 20, 24)); // nền giống giao diện ngoài
    wrapper.add(titleLabel, BorderLayout.NORTH);
    wrapper.add(chartPanel, BorderLayout.CENTER);

    return wrapper;
}

    }
