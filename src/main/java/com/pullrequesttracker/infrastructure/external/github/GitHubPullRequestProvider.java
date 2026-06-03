package com.pullrequesttracker.infrastructure.external.github;

import com.pullrequesttracker.application.provider.PlatformPullRequestProvider;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import com.pullrequesttracker.infrastructure.external.github.graphql.dto.GithubPullRequest;
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
public class GitHubPullRequestProvider implements PlatformPullRequestProvider {
    private static final long FALLBACK_LOOKBACK_HOURS = 24;
    private static final long OVERLAP_MINUTES = 1;

    private final GitHubGraphqlClientFactory gitHubClientFactory;
    private final GitHubPullRequestMapper mapper;
    private final TokenRepository tokenRepository;

    @Override
    public Platform platform() {
        return Platform.GITHUB;
    }

    @Override
    public List<PullRequestSyncData> fetch(CodeRepository codeRepository) {
        List<GithubPullRequest> pullRequests = fetchPullRequests(codeRepository);
        log.info("Found {} pull requests", pullRequests.size());
        return pullRequests.stream().map(mapper::toSyncData).toList();
    }

    private List<GithubPullRequest> fetchPullRequests(CodeRepository codeRepository) {
        TokenValue token = Optional.ofNullable(codeRepository.getTokenId())
                .flatMap(tokenRepository::findTokenValue)
                .orElse(null);

        HttpGraphQlClient graphQlClient = gitHubClientFactory.build(token);

        FullName fullName = codeRepository.getFullName();
        String since = Optional.ofNullable(codeRepository.getLastCheckedAt())
                .map(t -> t.minus(OVERLAP_MINUTES, ChronoUnit.MINUTES))
                .orElse(Instant.now().minus(FALLBACK_LOOKBACK_HOURS, ChronoUnit.HOURS))
                .truncatedTo(ChronoUnit.SECONDS)
                .toString();

        log.info("Fetching pull requests for {} since {}", fullName, since);

        String searchQuery = "repo:%s/%s is:pr updated:>%s".formatted(fullName.owner(), fullName.name(), since);

        return graphQlClient.documentName("github-pull-requests")
                .variable("searchQuery", searchQuery)
                .retrieve("search.nodes")
                .toEntityList(GithubPullRequest.class)
                .block();
    }
}
