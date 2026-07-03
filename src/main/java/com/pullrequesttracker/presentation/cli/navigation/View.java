package com.pullrequesttracker.presentation.cli.navigation;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.component.KeyBindingBar;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import dev.tamboui.layout.Rect;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.elements.DialogElement;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.panel;

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
        Element ui = dock().center(renderBody()).bottom(renderNavigationFooter());
        ui.render(frame, area, context);
        renderOverlay(frame, area, context);
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
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
