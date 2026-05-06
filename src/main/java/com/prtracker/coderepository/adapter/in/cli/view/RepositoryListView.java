package com.prtracker.coderepository.adapter.in.cli.view;

import com.prtracker.shared.cli.ViewName;
import com.prtracker.shared.cli.ViewComponent;
import com.prtracker.shared.cli.dialog.DialogManager;
import com.prtracker.shared.cli.view.View;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

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
