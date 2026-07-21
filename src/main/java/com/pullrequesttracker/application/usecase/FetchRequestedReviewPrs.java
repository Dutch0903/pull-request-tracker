package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.AttentionItemDto;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FetchRequestedReviewPrs {
    private final PullRequestRepository pullRequestRepository;
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final TokenRepository tokenRepository;

    public List<AttentionItemDto> execute() {
        Set<Actor> me = tokenRepository.findAll().stream()
                .map(t -> Actor.from(t.username().value()))
                .collect(Collectors.toSet());

        Map<CodeRepositoryId, FullName> names = codeRepositoryRepository.findAll().stream()
                .collect(Collectors.toMap(CodeRepository::getId, CodeRepository::getFullName));

        return pullRequestRepository.findAllOpen().stream()
                .filter(pr -> !pr.isDraft())
                .filter(pr -> me.stream().noneMatch(pr::isAuthoredBy))
                .filter(pr -> me.stream().anyMatch(pr::hasReviewRequestedFor))
                .sorted(Comparator.comparing(PullRequest::getCreatedAt))
                .map(pr -> toDto(pr, names.get(pr.getCodeRepositoryId())))
                .toList();
    }

    private AttentionItemDto toDto(PullRequest pr, FullName fullName) {
        String name = fullName != null ? fullName.toString() : "unknown/unknown";
        return new AttentionItemDto(
                pr.getId(),
                pr.getExternalId(),
                pr.getTitle().value(),
                name,
                pr.getReviewSummary().reviewStatus(),
                pr.getCiStatus(),
                pr.getCreatedAt(),
                "https://github.com/" + name + "/pull/" + pr.getExternalId()
        );
    }
}
