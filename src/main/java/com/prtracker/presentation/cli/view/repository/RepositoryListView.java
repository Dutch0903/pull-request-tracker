package com.prtracker.presentation.cli.view.repository;

import com.prtracker.presentation.cli.ViewName;
import com.prtracker.presentation.cli.ViewComponent;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.view.View;
import dev.tamboui.layout.Rect;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;

import static dev.tamboui.toolkit.Toolkit.*;

@ViewComponent(name = ViewName.REPOSITORIES)
public class RepositoryListView extends View {
    private final RepositoryListKeyHandler keyHandler;

    public RepositoryListView(DialogManager dialogManager, RepositoryListKeyHandler keyHandler) {
        super(dialogManager);

        this.keyHandler = keyHandler;
    }

    @Override
    protected Element renderBody() {
        return row(this.renderRepositoryList(), this.renderRepositoryStats());
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        return keyHandler.handle(event);
    }

    public Element renderRepositoryStats() {
        return panel(text("Stats")).fill();
    }

    public Element renderRepositoryList() {
        return panel(text("Repositories")).fill();
    }
}
