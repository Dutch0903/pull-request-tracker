package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import com.pullrequesttracker.presentation.cli.view.pullrequest.component.PullRequestList;
import dev.tamboui.toolkit.element.Element;

import java.util.Collections;
import java.util.List;

@ViewComponent(name = ViewName.PULL_REQUESTS)
public class PullRequestListView extends View {
    private final PullRequestListController controller;
    private final PullRequestListState state;
    private final PullRequestList pullRequestList;

    public PullRequestListView(DialogManager dialogManager, PullRequestListKeyHandler keyHandler,
            PullRequestListController controller, PullRequestListState state, PullRequestList pullRequestList) {
        super(dialogManager, keyHandler);
        this.controller = controller;
        this.state = state;
        this.pullRequestList = pullRequestList;
        this.controller.loadPullRequests();
    }

    @Override
    protected Element renderBody() {
        List<PullRequestListItemDto> items = state.get(PullRequestListState.PULL_REQUEST_ITEMS)
                .getOrElse(Collections.emptyList());
        return pullRequestList.render(items);
    }
}
