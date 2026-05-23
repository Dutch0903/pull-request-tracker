package com.prtracker.app.cli.navigation;

import com.prtracker.app.cli.action.KeyHandler;
import com.prtracker.app.cli.component.KeyBindingBar;
import com.prtracker.app.cli.dialog.DialogManager;
import dev.tamboui.layout.Rect;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.elements.DialogElement;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import static dev.tamboui.toolkit.Toolkit.*;

public abstract class View implements Element {
    protected final DialogManager dialogManager;
    protected final KeyHandler keyHandler;

    protected DialogElement renderedDialog;

    protected View(DialogManager dialogManager, KeyHandler keyHandler) {
        this.dialogManager = dialogManager;
        this.keyHandler = keyHandler;
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

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        return keyHandler.handle(event);
    }

    protected Element renderNavigationFooter() {
        return panel().add(new KeyBindingBar(keyHandler.getBindings()).render());
    }
}
