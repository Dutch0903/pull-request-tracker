package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import com.pullrequesttracker.presentation.cli.view.repository.component.RepositoryDetailPanel;
import com.pullrequesttracker.presentation.cli.view.repository.component.RepositoryList;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;

import static dev.tamboui.toolkit.Toolkit.row;

@ViewComponent(name = ViewName.REPOSITORIES)
public class RepositoryListView extends View {
    private final RepositoryListController controller;
    private final RepositoryList repositoryList;

    public RepositoryListView(DialogManager dialogManager, RepositoryListKeyHandler keyHandler,
            RepositoryListController controller, RepositoryList repositoryList) {
        super(dialogManager, keyHandler);
        this.controller = controller;
        this.repositoryList = repositoryList;
    }

    @Override
    protected Element renderBody() {
        controller.loadRepositories();
        return row(repositoryList.render(),
                new RepositoryDetailPanel(repositoryList.getSelectedRepository()).render().fill());
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }
}
