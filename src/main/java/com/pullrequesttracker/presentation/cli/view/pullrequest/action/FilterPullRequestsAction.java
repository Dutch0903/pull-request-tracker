package com.pullrequesttracker.presentation.cli.view.pullrequest.action;

import com.pullrequesttracker.presentation.cli.action.PullRequestListViewAction;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogHandler;
import com.pullrequesttracker.presentation.cli.view.pullrequest.FilterPullRequestsDialogConfiguration;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListController;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListState;
import com.pullrequesttracker.presentation.cli.view.pullrequest.filter.FilterDefinition;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FilterPullRequestsAction implements PullRequestListViewAction {
    private final DialogManager dialogManager;
    private final List<FilterDefinition> filterDefinitions;
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
        FilterPullRequestsDialogConfiguration config = new FilterPullRequestsDialogConfiguration(filterDefinitions,
                state);

        FormDialogHandler handler = values -> {
            filterDefinitions.forEach(def -> {
                String formValue = values.get(def.fieldId());
                state.setFilterValue(def.fieldId(), def.isActive(formValue) ? formValue : null);
            });
            controller.loadPullRequests();
        };

        dialogManager.openDialog(DialogType.FORM, config, handler);
    }
}
