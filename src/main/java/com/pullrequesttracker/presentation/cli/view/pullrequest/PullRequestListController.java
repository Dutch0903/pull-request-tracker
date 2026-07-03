package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.application.usecase.FindPullRequests;
import com.pullrequesttracker.domain.filter.PullRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PullRequestListController {
    private final PullRequestListState state;
    private final FindPullRequests findPullRequests;

    public void loadPullRequests() {
        List<PullRequestFilter> filters = PullRequestFilter.builder().codeRepository(state.getCodeRepositoryFilter())
                .status(state.getStatusFilter()).search(state.getSearchQuery()).build();

        state.set(PullRequestListState.PULL_REQUEST_ITEMS, findPullRequests.execute(filters));
    }
}
