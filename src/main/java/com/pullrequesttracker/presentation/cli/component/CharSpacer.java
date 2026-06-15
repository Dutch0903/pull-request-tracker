package com.pullrequesttracker.presentation.cli.component;

import dev.tamboui.layout.Constraint;
import dev.tamboui.layout.Rect;
import dev.tamboui.style.Style;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;

public final class CharSpacer implements Element {
    private final char fill;
    private Constraint layoutConstraint = Constraint.fill();

    private CharSpacer(char fill) {
        this.fill = fill;
    }

    public static CharSpacer of(char fill) {
        return new CharSpacer(fill);
    }

    public CharSpacer withWeight(int weight) {
        layoutConstraint = Constraint.fill(weight);
        return this;
    }

    public CharSpacer length(int length) {
        layoutConstraint = Constraint.length(length);
        return this;
    }

    public CharSpacer percent(int percent) {
        layoutConstraint = Constraint.percentage(percent);
        return this;
    }

    @Override
    public Constraint constraint() {
        return layoutConstraint;
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        if (layoutConstraint instanceof Constraint.Length l) {
            return Size.of(l.value(), l.value());
        }
        return Size.UNKNOWN;
    }

    @Override
    public void render(Frame frame, Rect rect, RenderContext context) {
        if (rect.isEmpty()) return;
        Style style = context.currentStyle();
        String row = String.valueOf(fill).repeat(rect.width());
        for (int y = rect.top(); y < rect.bottom(); y++) {
            frame.buffer().setString(rect.x(), y, row, style);
        }
    }
}
