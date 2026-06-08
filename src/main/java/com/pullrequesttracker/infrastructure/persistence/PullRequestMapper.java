package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.model.PullRequestFactory;
import com.pullrequesttracker.domain.model.PullRequestState;
import com.pullrequesttracker.domain.type.CiStatus;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.type.ReviewStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.MergeInfo;
import com.pullrequesttracker.domain.valueobject.PullRequestId;
import com.pullrequesttracker.domain.valueobject.Review;
import com.pullrequesttracker.domain.valueobject.ReviewSummary;
import com.pullrequesttracker.infrastructure.persistence.dto.PullRequestDto;
import com.pullrequesttracker.infrastructure.persistence.dto.ReviewDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PullRequestMapper {

    public PullRequestDto toDto(PullRequest pullRequest) {
        List<ReviewDto> reviewDtos = pullRequest.getReviewSummary().reviews().stream()
                .map(r -> new ReviewDto(r.reviewer(), r.status().name(), r.submittedAt())).toList();

        return new PullRequestDto(pullRequest.getId().value(), pullRequest.getCodeRepositoryId().value(),
                pullRequest.getExternalId(), pullRequest.getAuthor(), pullRequest.getCreatedAt(),
                pullRequest.getTitle(), pullRequest.isDraft(), pullRequest.getStatus().name(),
                pullRequest.getCiStatus().name(), List.copyOf(pullRequest.getLabels()), reviewDtos,
                pullRequest.getReviewSummary().reviewStatus().name(), pullRequest.getCommentCount(),
                pullRequest.getMergeInfo().map(MergeInfo::mergedBy).orElse(null),
                pullRequest.getMergeInfo().map(MergeInfo::mergedAt).orElse(null), pullRequest.getUpdatedAt());
    }

    public PullRequest toDomain(PullRequestDto dto) {
        List<Review> reviews = dto.reviews().stream()
                .map(r -> new Review(r.reviewer(), ReviewStatus.valueOf(r.state()), r.submittedAt())).toList();

        ReviewSummary reviewSummary = new ReviewSummary(reviews, ReviewStatus.valueOf(dto.reviewStatus()));

        PullRequestState state = switch (PullRequestStatus.valueOf(dto.status())) {
            case MERGED -> new PullRequestState.Merged(new MergeInfo(dto.mergedBy(), dto.mergedAt()));
            case CLOSED -> new PullRequestState.Closed();
            case IGNORED -> new PullRequestState.Ignored();
            default -> new PullRequestState.Open();
        };

        return PullRequestFactory.reconstitute(new PullRequestId(dto.id()),
                new CodeRepositoryId(dto.codeRepositoryId()), dto.externalId(), dto.author(), dto.createdAt(),
                dto.title(), dto.draft(), state, CiStatus.valueOf(dto.ciStatus()), dto.labels(), reviewSummary,
                dto.commentCount(), dto.updatedAt());
    }
}
