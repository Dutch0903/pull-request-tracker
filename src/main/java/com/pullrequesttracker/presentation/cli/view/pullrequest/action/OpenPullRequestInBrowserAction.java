package com.pullrequesttracker.presentation.cli.view.pullrequest.action;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.presentation.cli.action.OpenInBrowserAction;
import com.pullrequesttracker.presentation.cli.action.PullRequestListViewAction;
import com.pullrequesttracker.presentation.cli.view.pullrequest.component.PullRequestList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenPullRequestInBrowserAction extends OpenInBrowserAction implements PullRequestListViewAction {
    private final PullRequestList pullRequestList;

    @Override
    protected String getUrl() {
        PullRequestListItemDto item = pullRequestList.getSelectedItem();
        return item != null ? item.url() : null;
    }
}
