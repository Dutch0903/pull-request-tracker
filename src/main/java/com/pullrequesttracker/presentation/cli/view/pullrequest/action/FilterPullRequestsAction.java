package com.pullrequesttracker.presentation.cli.view.pullrequest.action;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.application.usecase.FetchAllCodeRepositories;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.presentation.cli.action.PullRequestListViewAction;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogHandler;
import com.pullrequesttracker.presentation.cli.view.pullrequest.FilterFormFields;
import com.pullrequesttracker.presentation.cli.view.pullrequest.FilterPullRequestsDialogConfiguration;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListController;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListState;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FilterPullRequestsAction implements PullRequestListViewAction {
    private final DialogManager dialogManager;
    private final FetchAllCodeRepositories fetchAllCodeRepositories;
    private final PullRequestListState state;
    private final PullRequestListController controller;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.character() == 'f';
    }

    @Override
    public String getKey() {
        return "f";
    }

    @Override
    public String getLabel() {
        return "Filter";
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public void execute(KeyEvent event) {
        List<CodeRepositoryDto> repos = fetchAllCodeRepositories.execute();
        FilterPullRequestsDialogConfiguration config = new FilterPullRequestsDialogConfiguration(repos,
                state.getStatusFilter(), state.getSearchQuery(), state.getCodeRepositoryFilter());

        FormDialogHandler handler = values -> {
            String statusValue = values.get(FilterFormFields.STATUS);
            state.setStatusFilter(FilterPullRequestsDialogConfiguration.STATUS_OPTIONS.get(statusValue));

            String searchValue = values.get(FilterFormFields.SEARCH);
            state.setSearchQuery(searchValue != null && !searchValue.isBlank() ? searchValue : null);

            String repoValue = values.get(FilterFormFields.REPOSITORY);
            if (repoValue == null || repoValue.equals(FilterPullRequestsDialogConfiguration.ALL)) {
                state.setCodeRepositoryFilter(null);
            } else {
                repos.stream().filter(r -> (r.owner() + "/" + r.name()).equals(repoValue))
                        .map(r -> CodeRepositoryId.from(r.id())).findFirst().ifPresent(state::setCodeRepositoryFilter);
            }

            controller.loadPullRequests();
        };

        dialogManager.openDialog(DialogType.FORM, config, handler);
    }
}
