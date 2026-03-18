package com.prtracker.presentation.cli.view;

import dev.tamboui.layout.Rect;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;

import static dev.tamboui.toolkit.Toolkit.*;

public abstract class View implements Element {
    @Override
    public void render(Frame frame, Rect area, RenderContext context) {
        Element ui = dock().center(this.renderBody()).bottom(this.renderNavigationFooter());

        ui.render(frame, area, context);

        this.renderOverlay(frame, area, context);
    }

    protected abstract Element renderBody();

    protected void renderOverlay(Frame frame, Rect area, RenderContext renderContext) {
        // By default, don't render anything
    }

    protected Element renderNavigationFooter() {
        return panel().borderless();
    }
}
