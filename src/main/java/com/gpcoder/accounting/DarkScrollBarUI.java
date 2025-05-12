package com.gpcoder.accounting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * ScrollBar UI tối màu – dày 10 px, thumb bo tròn, không có nút mũi tên.
 * Tương thích JDK 8 trở lên (không dùng switch‑expression).
 */
public class DarkScrollBarUI extends BasicScrollBarUI {

    private static final int BAR_WIDTH = 10;      // độ dày thanh cuộn
    private static final int ARC       = 10;      // bo góc thumb/track

    private final Color trackColor = new Color(36, 40, 45);    // màu nền track
    private final Color thumbColor = new Color( 90, 92, 95);   // thumb bình thường
    private final Color hoverColor = new Color(110,113,116);   // thumb hover
    private final Color dragColor  = new Color(130,133,136);   // thumb khi kéo

    /* Ẩn 2 nút mũi tên mặc định */
    @Override protected JButton createDecreaseButton(int o) { return createZeroButton(); }
    @Override protected JButton createIncreaseButton(int o) { return createZeroButton(); }

    private JButton createZeroButton() {
        JButton b = new JButton();
        b.setPreferredSize(new Dimension(0, 0));
        b.setOpaque(false);
        b.setFocusable(false);
        b.setBorderPainted(false);
        return b;
    }

    /* Thumb tối thiểu để dễ bấm */
    @Override protected Dimension getMinimumThumbSize() {
        return scrollbar.getOrientation() == JScrollBar.VERTICAL
            ? new Dimension(BAR_WIDTH, 60)
            : new Dimension(60, BAR_WIDTH);
    }

    /* Vẽ nền track */
    @Override protected void paintTrack(Graphics g, JComponent c, Rectangle r) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(trackColor);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, ARC, ARC);
        g2.dispose();
    }

    /* Vẽ thumb (màu thay đổi khi hover/drag) */
    @Override protected void paintThumb(Graphics g, JComponent c, Rectangle r) {
        if (!scrollbar.isEnabled() ||
            (r.width > r.height && scrollbar.getOrientation() == JScrollBar.VERTICAL)) {
            return;                       // không vẽ khi disabled
        }
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color color;                      // thay switch-expression bằng if/else
        if (isDragging)          color = dragColor;
        else if (isThumbRollover()) color = hoverColor;
        else                      color = thumbColor;

        g2.setColor(color);
        g2.fillRoundRect(r.x, r.y, r.width, r.height, ARC, ARC);
        g2.dispose();
    }

    /* Cố định bề rộng/thấp của scrollBar */
    @Override protected void layoutVScrollbar(JScrollBar sb) {
        super.layoutVScrollbar(sb);
        sb.setPreferredSize(new Dimension(BAR_WIDTH, Integer.MAX_VALUE));
    }

    @Override protected void layoutHScrollbar(JScrollBar sb) {
        super.layoutHScrollbar(sb);
        sb.setPreferredSize(new Dimension(Integer.MAX_VALUE, BAR_WIDTH));
    }
}
