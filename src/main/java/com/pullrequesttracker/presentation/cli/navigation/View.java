package com.pullrequesttracker.presentation.cli.navigation;

import com.pullrequesttracker.infrastructure.config.ViewRefreshProperties;
import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.component.CountdownTimer;
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

import java.time.Duration;
import java.time.Instant;

import static dev.tamboui.toolkit.Toolkit.*;

public abstract class View implements Element {
    protected final DialogManager dialogManager;
    protected final KeyHandler keyHandler;
    protected DialogElement renderedDialog;

    private Instant lastRefreshedAt = Instant.EPOCH;
    private final Duration refreshInterval;

    protected View(DialogManager dialogManager, KeyHandler keyHandler, ViewRefreshProperties viewRefreshProperties) {
        this.dialogManager = dialogManager;
        this.keyHandler = keyHandler;
        this.refreshInterval = Duration.ofMillis(viewRefreshProperties.intervalMs());
    }

    void triggerRefresh() {
        refreshState();
        lastRefreshedAt = Instant.now();
    }

    protected abstract void refreshState();

    @Override
    public void render(Frame frame, Rect area, RenderContext context) {
        if (Duration.between(lastRefreshedAt, Instant.now()).compareTo(refreshInterval) > 0) {
            triggerRefresh();
        }
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
        return panel().add(row(new KeyBindingBar(keyHandler.getBindings()).render(),
                spacer(),
                new CountdownTimer(lastRefreshedAt, refreshInterval).render()));
    }
}
