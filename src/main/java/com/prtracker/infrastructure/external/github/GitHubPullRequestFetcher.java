package com.prtracker.infrastructure.external.github;

import com.prtracker.application.service.PullRequestFetcher;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.exceptions.TokenNotFoundException;
import com.prtracker.domain.repository.TokenRepository;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GitHubPullRequestFetcher implements PullRequestFetcher {
    private final TokenRepository tokenRepository;
    private final GitHubPullRequestMapper mapper;

    @Override
    public List<PullRequest> fetchPullRequests(CodeRepository codeRepository) {
        try {
            GitHub github = buildClient(codeRepository);

            return github.getRepository(codeRepository.getFullName().toString()).getPullRequests(GHIssueState.OPEN)
                    .stream().map(ghPullRequest -> this.mapSafely(codeRepository.getId(), ghPullRequest)).toList();

        } catch (IOException e) {
            throw new GitHubApiException("Failed to fetch pull requests for %s".formatted(codeRepository.getFullName()),
                    e);
        }
    }

    private GitHub buildClient(CodeRepository codeRepository) throws IOException {
        if (codeRepository.getTokenId() == null) {
            return GitHub.connectAnonymously();
        }

        String tokenValue = tokenRepository.findById(codeRepository.getTokenId())
                .orElseThrow(() -> new TokenNotFoundException(codeRepository.getTokenId())).getValue();

        return new GitHubBuilder().withOAuthToken(tokenValue).build();
    }

    private PullRequest mapSafely(CodeRepositoryId codeRepositoryId, GHPullRequest ghPr) {
        try {
            return mapper.map(ghPr, codeRepositoryId);
        } catch (IOException e) {
            throw new GitHubApiException("Failed to map pull request #" + ghPr.getNumber(), e);
        }
    }
}
