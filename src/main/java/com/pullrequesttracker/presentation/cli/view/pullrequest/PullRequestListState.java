package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.presentation.cli.state.SnapshotKey;
import com.pullrequesttracker.presentation.cli.state.StateManager;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class PullRequestListState extends StateManager {
    public static final SnapshotKey<List<PullRequestListItemDto>> PULL_REQUEST_ITEMS = new SnapshotKey<>(
            "pullRequestItems", Duration.ofMinutes(1));

    private CodeRepositoryId codeRepositoryFilter;
    private PullRequestStatus statusFilter;
    private String searchQuery;

    public CodeRepositoryId getCodeRepositoryFilter() {
        return codeRepositoryFilter;
    }

    public void setCodeRepositoryFilter(CodeRepositoryId repoFilter) {
        this.codeRepositoryFilter = repoFilter;
    }

    public PullRequestStatus getStatusFilter() {
        return statusFilter;
    }

    public void setStatusFilter(PullRequestStatus statusFilter) {
        this.statusFilter = statusFilter;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public void resetFilters() {
        this.statusFilter = null;
        this.searchQuery = null;
        this.codeRepositoryFilter = null;
    }
}
