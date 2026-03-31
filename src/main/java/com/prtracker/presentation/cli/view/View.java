package com.prtracker.presentation.cli.view;

import com.prtracker.presentation.cli.dialog.DialogManager;
import dev.tamboui.layout.Rect;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.elements.DialogElement;

import static dev.tamboui.toolkit.Toolkit.*;

public abstract class View implements Element {
    protected final DialogManager dialogManager;

    protected DialogElement renderedDialog;

    protected View(DialogManager dialogManager) {
        this.dialogManager = dialogManager;
    }

    @Override
    public void render(Frame frame, Rect area, RenderContext context) {
        Element ui = dock().center(this.renderBody()).bottom(this.renderNavigationFooter());

        ui.render(frame, area, context);

        this.renderOverlay(frame, area, context);
    }

    protected abstract Element renderBody();

    protected void renderOverlay(Frame frame, Rect area, RenderContext context) {
        if (!dialogManager.isDialogOpen()) {
            renderedDialog = null;
            return;
        }

        renderedDialog = dialogManager.getCurrentDialog().render();
        renderedDialog.render(frame, area, context);
    }

    protected Element renderNavigationFooter() {
        return panel().borderless();
    }
}
