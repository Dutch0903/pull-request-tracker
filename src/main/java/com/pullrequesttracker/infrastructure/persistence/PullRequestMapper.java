package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestFactory;
import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.Actor;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.PullRequestId;
import com.pullrequesttracker.domain.valueobject.Title;
import com.pullrequesttracker.domain.valueobject.Review;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import com.pullrequesttracker.infrastructure.persistence.dto.PullRequestDto;
import com.pullrequesttracker.infrastructure.persistence.dto.ReviewDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class PullRequestMapper {

    public PullRequestDto toDto(PullRequest pullRequest) {
        List<ReviewDto> reviewDtos = pullRequest.getReviewSummary().reviews().stream()
                .map(r -> new ReviewDto(r.reviewer().value(), r.status().name(), r.submittedAt())).toList();

        return new PullRequestDto(pullRequest.getId().value(), pullRequest.getCodeRepositoryId().value(),
                pullRequest.getExternalId(), pullRequest.getAuthor().value(), pullRequest.getCreatedAt(),
                pullRequest.getTitle().value(), pullRequest.isDraft(), pullRequest.getStatus().name(),
                pullRequest.getCiStatus().name(), List.copyOf(pullRequest.getLabels()), reviewDtos,
                pullRequest.getReviewSummary().reviewStatus().name(), pullRequest.getReviewSummary().requestedReviewers().stream().map(Actor::toString).toList(), pullRequest.getCommentCount(),
                pullRequest.getMergeInfo().map(m -> m.mergedBy().value()).orElse(null),
                pullRequest.getMergeInfo().map(MergeInfo::mergedAt).orElse(null), pullRequest.getUpdatedAt());
    }

    public PullRequest toDomain(PullRequestDto dto) {
        List<Review> reviews = dto.reviews().stream()
                .map(r -> new Review(Actor.from(r.reviewer()), ReviewStatus.valueOf(r.state()), r.submittedAt()))
                .toList();

        Set<Actor> reviewRequests = dto.requestedReviewers().stream()
                .map(Actor::new)
                .collect(Collectors.toSet());

        ReviewSummary reviewSummary = new ReviewSummary(reviews, ReviewStatus.valueOf(dto.reviewStatus()), reviewRequests);

        PullRequestState state = switch (PullRequestStatus.valueOf(dto.status())) {
            case MERGED -> new PullRequestState.Merged(new MergeInfo(Actor.from(dto.mergedBy()), dto.mergedAt()));
            case CLOSED -> new PullRequestState.Closed();
            case IGNORED -> new PullRequestState.Ignored();
            default -> new PullRequestState.Open();
        };

        return PullRequestFactory.reconstitute(new PullRequestId(dto.id()),
                CodeRepositoryId.from(dto.codeRepositoryId()), dto.externalId(), Actor.from(dto.author()),
                dto.createdAt(), new Title(dto.title()), dto.draft(), state, CiStatus.valueOf(dto.ciStatus()),
                dto.labels(), reviewSummary, dto.commentCount(), dto.updatedAt());
    }
}
