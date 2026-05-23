package com.prtracker.app.cli.view.repository;

import com.prtracker.app.cli.navigation.ViewComponent;
import com.prtracker.app.cli.navigation.ViewName;
import com.prtracker.app.cli.dialog.DialogManager;
import com.prtracker.app.cli.navigation.View;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;

import static dev.tamboui.toolkit.Toolkit.*;

@ViewComponent(name = ViewName.REPOSITORIES)
public class RepositoryListView extends View {
    public RepositoryListView(DialogManager dialogManager, RepositoryListKeyHandler keyHandler) {
        super(dialogManager, keyHandler);
    }

    @Override
    protected Element renderBody() {
        return row(this.renderRepositoryList(), this.renderRepositoryStats());
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    public Element renderRepositoryStats() {
        return panel(text("Stats")).fill();
    }

    public Element renderRepositoryList() {
        return panel(text("Repositories")).fill();
    }
}
