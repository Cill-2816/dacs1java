package com.gpcoder.chatbox;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Element;
import javax.swing.text.LabelView;
import javax.swing.text.StyledEditorKit;
import javax.swing.text.View;
import javax.swing.text.ViewFactory;

public class WrapAnywhereKit extends StyledEditorKit {
    private final ViewFactory defaultFactory = new WrapFactory();

    @Override
    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

    static class WrapFactory implements ViewFactory {
        @Override
        public View create(Element elem) {
            String kind = elem.getName();
            if (kind != null && kind.equals(AbstractDocument.ContentElementName)) {
                return new WrapLabelView(elem);
            }
            return new StyledEditorKit().getViewFactory().create(elem);
        }
    }

    static class WrapLabelView extends LabelView {
        public WrapLabelView(Element elem) {
            super(elem);
        }

        @Override
        public float getMinimumSpan(int axis) {
            return axis == View.X_AXIS ? 0 : super.getMinimumSpan(axis);
        }
    }
}
