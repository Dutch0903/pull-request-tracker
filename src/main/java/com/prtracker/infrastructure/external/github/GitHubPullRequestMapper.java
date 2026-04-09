package com.prtracker.infrastructure.external.github;

import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.enums.PullRequestStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.PullRequestId;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHubPullRequestMapper {
    public PullRequest map(GHPullRequest ghPr, CodeRepositoryId codeRepositoryId) throws IOException {
        return new PullRequest(PullRequestId.create(), codeRepositoryId, ghPr.getNumber(), ghPr.getTitle(),
                ghPr.getAssignee().getName(), mapStatus(ghPr.getState()), ghPr.getUpdatedAt().toInstant());
    }

    private PullRequestStatus mapStatus(GHIssueState status) {
        return PullRequestStatus.OPEN;
    }
}
