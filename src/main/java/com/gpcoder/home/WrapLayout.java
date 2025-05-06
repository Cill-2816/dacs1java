package com.gpcoder.home;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;

/**
 * FlowLayout mở rộng – cho phép wrap component khi dòng đầy.
 * Thêm vào project để dùng cho panel chứa card món ăn.
 */
public class WrapLayout extends FlowLayout {

    public WrapLayout()                          { super(); }
    public WrapLayout(int align)                 { super(align); }
    public WrapLayout(int align, int hgap, int vgap) { super(align, hgap, vgap); }

    @Override
    public Dimension preferredLayoutSize(Container target) {
        return layoutSize(target, true);
    }

    @Override
    public Dimension minimumLayoutSize(Container target) {
        Dimension minimum = layoutSize(target, false);
        minimum.width -= (getHgap() + 1);
        return minimum;
    }

    private Dimension layoutSize(Container target, boolean preferred) {
        synchronized (target.getTreeLock()) {

            int hgap = getHgap();
            int vgap = getVgap();
            int maxWidth = target.getWidth();

            if (maxWidth <= 0) {
                Container parent = target;
                while (parent.getWidth() <= 0 && parent.getParent() != null) {
                    parent = parent.getParent();
                }
                maxWidth = parent.getWidth();
            }

            Insets  insets      = target.getInsets();
            maxWidth            -= insets.left + insets.right + hgap * 2;
            int   x             = 0;
            int   y             = vgap;
            int   rowHeight     = 0;

            for (Component m : target.getComponents()) {
                if (!m.isVisible()) continue;

                Dimension d = preferred ? m.getPreferredSize() : m.getMinimumSize();

                if (x == 0 || x + d.width <= maxWidth) {
                    if (x > 0) x += hgap;
                    x += d.width;
                    rowHeight = Math.max(rowHeight, d.height);
                } else {
                    x = d.width;
                    y += vgap + rowHeight;
                    rowHeight = d.height;
                }
            }

            y += rowHeight;
            y += insets.bottom;
            return new Dimension(maxWidth, y);
        }
    }
}
