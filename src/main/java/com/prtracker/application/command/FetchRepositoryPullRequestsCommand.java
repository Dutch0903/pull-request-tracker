package com.prtracker.application.command;

import com.prtracker.application.service.PullRequestFetcher;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.repository.PullRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchRepositoryPullRequestsCommand extends Command<CodeRepository, Void> {
    private final PullRequestFetcher pullRequestFetcher;
    private final PullRequestRepository pullRequestRepository;

    @Override
    protected Void executeInternal(CodeRepository codeRepository) {
        List<PullRequest> pullRequests = pullRequestFetcher.fetchPullRequests(codeRepository);

        pullRequests.forEach(pullRequestRepository::save);

        return null;
    }
}
