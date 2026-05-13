package com.prtracker.pullrequest.adapter.out.github;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.FullName;
import com.prtracker.pullrequest.adapter.out.github.graphql.GitHubGraphqlClientFactory;
import com.prtracker.pullrequest.adapter.out.github.graphql.dto.PullRequest;
import com.prtracker.pullrequest.domain.model.PullRequestSyncData;
import com.prtracker.pullrequest.domain.port.RepositorySynchronizerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitHubRepositorySynchronizer implements RepositorySynchronizerPort {
    private static final long FALLBACK_LOOKBACK_HOURS = 24;
    private static final long OVERLAP_MINUTES = 1;

    private final GitHubGraphqlClientFactory gitHubClientFactory;
    private final GitHubPullRequestMapper mapper;

    @Override
    public List<PullRequestSyncData> synchronize(CodeRepository codeRepository) {
        List<PullRequest> pullRequests = fetchPullRequests(codeRepository);

        log.info("Found {} pull requests", pullRequests.size());

        return pullRequests.stream().map(mapper::toSyncData).toList();
    }

    private List<PullRequest> fetchPullRequests(CodeRepository codeRepository) {
        HttpGraphQlClient graphQlClient = gitHubClientFactory.build(codeRepository);

        FullName fullName = codeRepository.getFullName();
        String since = Optional.ofNullable(codeRepository.getLastCheckedAt())
                .map(t -> t.minus(OVERLAP_MINUTES, ChronoUnit.MINUTES))
                .orElse(Instant.now().minus(FALLBACK_LOOKBACK_HOURS, ChronoUnit.HOURS)).truncatedTo(ChronoUnit.SECONDS)
                .toString();

        log.info("Fetching pull requests for {} since {}", fullName, since);

        String searchQuery = "repo:%s/%s is:pr updated:>%s".formatted(fullName.owner(), fullName.name(), since);

        return graphQlClient.documentName("github-pull-requests").variable("searchQuery", searchQuery)
                .retrieve("search.nodes").toEntityList(PullRequest.class).block();
    }
}
