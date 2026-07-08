package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.application.usecase.FindPullRequests;
import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.presentation.cli.view.pullrequest.filter.FilterDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PullRequestListController {
    private final PullRequestListState state;
    private final FindPullRequests findPullRequests;
    private final List<FilterDefinition> filterDefinitions;

    public void loadPullRequests() {
        List<PullRequestFilter> filters = filterDefinitions.stream()
                .map(def -> def.toDomainFilter(state.getFilterValue(def.fieldId()))).flatMap(Optional::stream).toList();

        state.set(PullRequestListState.PULL_REQUEST_ITEMS, findPullRequests.execute(filters));
    }
}
