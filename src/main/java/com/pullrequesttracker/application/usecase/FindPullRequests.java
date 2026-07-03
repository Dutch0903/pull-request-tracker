package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindPullRequests {
    private final PullRequestRepository pullRequestRepository;
    private final CodeRepositoryRepository codeRepositoryRepository;

    public List<PullRequestListItemDto> execute(List<PullRequestFilter> filters) {
        List<PullRequest> pullRequests = pullRequestRepository.findAll(filters);

        Map<CodeRepositoryId, FullName> repoFullNames = codeRepositoryRepository.findAll().stream()
                .collect(Collectors.toMap(CodeRepository::getId, CodeRepository::getFullName));

        return pullRequests.stream().sorted((a, b) -> b.getUpdatedAt().compareTo(a.getUpdatedAt()))
                .map(pr -> toDto(pr, repoFullNames.get(pr.getCodeRepositoryId()))).toList();
    }

    private PullRequestListItemDto toDto(PullRequest pr, FullName fullName) {
        String fullNameStr = fullName != null ? fullName.toString() : "unknown/unknown";
        String url = "https://github.com/" + fullNameStr + "/pull/" + pr.getExternalId();

        return new PullRequestListItemDto(pr.getId(), pr.getExternalId(), pr.getTitle().value(), pr.getAuthor().value(),
                pr.getUpdatedAt(), pr.isDraft(), pr.getCiStatus(), pr.getReviewSummary().approvalCount(),
                pr.getReviewSummary().reviewStatus(), pr.getCommentCount(), pr.getLabels(), fullNameStr, url);
    }
}
