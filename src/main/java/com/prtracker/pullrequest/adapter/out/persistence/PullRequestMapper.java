package com.prtracker.pullrequest.adapter.out.persistence;

import com.prtracker.pullrequest.domain.enums.CiStatus;
import com.prtracker.pullrequest.domain.enums.PullRequestStatus;
import com.prtracker.pullrequest.domain.enums.ReviewStatus;
import com.prtracker.pullrequest.domain.model.PullRequest;
import com.prtracker.pullrequest.domain.model.PullRequestFactory;
import com.prtracker.pullrequest.domain.model.PullRequestId;
import com.prtracker.pullrequest.domain.model.Review;
import com.prtracker.shared.kernel.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PullRequestMapper {
    private final PullRequestFactory pullRequestFactory;

    public PullRequestDto toDto(PullRequest pullRequest) {
        List<ReviewDto> reviewDtos = pullRequest.getReviews().stream()
                .map(r -> new ReviewDto(r.reviewer(), r.state().name(), r.submittedAt())).toList();

        return new PullRequestDto(pullRequest.getId().value(), pullRequest.getCodeRepositoryId().value(),
                pullRequest.getExternalId(), pullRequest.getTitle(), pullRequest.getAuthor(), pullRequest.isDraft(),
                pullRequest.getStatus().name(), pullRequest.getCiStatus().name(), pullRequest.getCommentCount(),
                List.copyOf(pullRequest.getLabels()), List.copyOf(pullRequest.getRequestedReviewers()), reviewDtos,
                pullRequest.getReviewStatus().name(), pullRequest.getCreatedAt(), pullRequest.getUpdatedAt(),
                pullRequest.getMergedBy(), pullRequest.getMergedAt());
    }

    public PullRequest toDomain(PullRequestDto dto) {
        List<Review> reviews = dto.reviews().stream()
                .map(r -> new Review(r.reviewer(), ReviewStatus.valueOf(r.state()), r.submittedAt())).toList();

        return pullRequestFactory.reconstitute(PullRequestId.from(dto.id()),
                CodeRepositoryId.from(dto.codeRepositoryId()), dto.externalId(), dto.title(), dto.author(), dto.draft(),
                PullRequestStatus.valueOf(dto.status()), CiStatus.valueOf(dto.ciStatus()), dto.commentCount(),
                dto.labels(), dto.requestedReviewers(), reviews, ReviewStatus.valueOf(dto.reviewStatus()),
                dto.createdAt(), dto.updatedAt(), dto.mergedBy(), dto.mergedAt());
    }
}
