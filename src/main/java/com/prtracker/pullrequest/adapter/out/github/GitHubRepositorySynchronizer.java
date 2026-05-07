package com.prtracker.pullrequest.adapter.out.github;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.model.PullRequest;
import com.prtracker.pullrequest.domain.model.PullRequestFactory;
import com.prtracker.pullrequest.domain.port.RepositorySynchronizerPort;
import com.prtracker.shared.kernel.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitHubRepositorySynchronizer implements RepositorySynchronizerPort {
    private final GitHubClientFactory clientFactory;
    private final GitHubPullRequestMapper mapper;
    private final PullRequestFactory pullRequestFactory;

    @Override
    public List<PullRequest> synchronize(CodeRepository codeRepository, List<PullRequest> existingPullRequests) {
        try {
            List<GitHubPullRequestData> rawData = fetchRawData(codeRepository);

            log.info("Synchronizing {} pull requests", rawData.size());

            Map<Integer, PullRequest> existingByExternalId = existingPullRequests.stream()
                    .collect(Collectors.toMap(PullRequest::getExternalId, Function.identity()));

            return rawData.stream()
                    .map(data -> updateOrCreate(data, codeRepository.getId(), existingByExternalId))
                    .toList();
        } catch (IOException e) {
            throw new GitHubApiException("Failed to synchronize " + codeRepository.getFullName(), e);
        }
    }

    private List<GitHubPullRequestData> fetchRawData(CodeRepository codeRepository) throws IOException {
        GitHub github = clientFactory.build(codeRepository);
        return github.getRepository(codeRepository.getFullName().toString())
                .getPullRequests(GHIssueState.OPEN)
                .stream()
                .map(this::collectSafely)
                .toList();
    }

    private GitHubPullRequestData collectSafely(GHPullRequest ghPullRequest) {
        try {
            List<GHPullRequestReview> reviews = ghPullRequest.listReviews().toList();
            List<GHCheckRun> checkRuns = ghPullRequest.getRepository()
                    .getCheckRuns(ghPullRequest.getHead().getSha()).toList();
            return new GitHubPullRequestData(ghPullRequest, reviews, checkRuns);
        } catch (IOException e) {
            throw new GitHubApiException("Failed to collect data for PR #" + ghPullRequest.getNumber(), e);
        }
    }

    private PullRequest updateOrCreate(GitHubPullRequestData data, CodeRepositoryId codeRepositoryId,
                                       Map<Integer, PullRequest> existingByExternalId) {
        GHPullRequest ghPr = data.pullRequest();
        try {
            PullRequest pr = existingByExternalId.containsKey(ghPr.getNumber())
                    ? existingByExternalId.get(ghPr.getNumber())
                    : createPullRequest(data, codeRepositoryId);

            mapper.mapReviews(data.reviews()).forEach(pr::addReview);
            pr.updateCiStatus(mapper.mapCiStatus(data.checkRuns()), ghPr.getUpdatedAt().toInstant());

            applyStatusChange(pr, ghPr);

            pr.updateTitle(ghPr.getTitle(), ghPr.getUpdatedAt().toInstant());
            pr.updateLabels(mapper.mapLabels(ghPr), ghPr.getUpdatedAt().toInstant());
            pr.updateRequestedReviewers(mapper.mapRequestedReviewers(ghPr), ghPr.getUpdatedAt().toInstant());
            pr.updateCommentCount(ghPr.getCommentsCount());

            return pr;
        } catch (IOException e) {
            throw new GitHubApiException("Failed to process PR #" + ghPr.getNumber(), e);
        }
    }

    private PullRequest createPullRequest(GitHubPullRequestData data, CodeRepositoryId codeRepositoryId) throws IOException {
        GHPullRequest ghPr = data.pullRequest();
        return pullRequestFactory.open(
                codeRepositoryId,
                ghPr.getNumber(),
                ghPr.getTitle(),
                ghPr.getUser().getLogin(),
                ghPr.isDraft(),
                mapper.mapCiStatus(data.checkRuns()),
                ghPr.getCommentsCount(),
                mapper.mapLabels(ghPr),
                mapper.mapRequestedReviewers(ghPr),
                ghPr.getCreatedAt().toInstant(),
                ghPr.getUpdatedAt().toInstant()
        );
    }

    private void applyStatusChange(PullRequest pr, GHPullRequest ghPr) throws IOException {
        PullRequestStatus status = mapper.mapStatus(ghPr);

        if (status == PullRequestStatus.MERGED) {
            pr.merge(ghPr.getMergedBy().getLogin(), ghPr.getMergedAt().toInstant());
        } else if (status == PullRequestStatus.CLOSED) {
            pr.close(ghPr.getUpdatedAt().toInstant());
        } else if (!ghPr.isDraft()) {
            pr.undraft(ghPr.getUpdatedAt().toInstant());
        }
    }
}
