package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.application.dto.CodeRepositoryStatisticsDto;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.presentation.cli.navigation.ViewRefreshConfiguration;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import com.pullrequesttracker.presentation.cli.view.repository.component.RepositoryDetailPanel;
import com.pullrequesttracker.presentation.cli.view.repository.component.RepositoryList;
import dev.tamboui.toolkit.element.Element;

import static dev.tamboui.toolkit.Toolkit.row;

@ViewComponent(name = ViewName.REPOSITORIES)
public class RepositoryListView extends View {
    private final RepositoryListController controller;
    private final RepositoryListState state;
    private final RepositoryList repositoryList;

    public RepositoryListView(DialogManager dialogManager, RepositoryListKeyHandler keyHandler,
            RepositoryListController controller, RepositoryListState state, RepositoryList repositoryList,
            ViewRefreshConfiguration viewRefreshProperties) {
        super(dialogManager, keyHandler, viewRefreshProperties);
        this.controller = controller;
        this.state = state;
        this.repositoryList = repositoryList;
    }

    @Override
    protected void refreshState() {
        controller.loadRepositories();
    }

    @Override
    protected Element renderBody() {
        CodeRepositoryDto selected = repositoryList.getSelectedRepository();
        CodeRepositoryStatisticsDto currentStats = state.get(RepositoryListState.REPOSITORY_STATS);

        if (selected != null) {
            boolean wrongRepo = currentStats == null || !currentStats.codeRepositoryId().equals(selected.id());
            if (wrongRepo) {
                controller.loadRepositoryStats(CodeRepositoryId.from(selected.id()));
                currentStats = state.get(RepositoryListState.REPOSITORY_STATS);
            }
        }

        return row(repositoryList.render(), new RepositoryDetailPanel(selected, currentStats).render().fill());
    }
}
