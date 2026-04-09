package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.enums.PullRequestStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.PullRequestId;
import com.prtracker.infrastructure.persistence.dto.PullRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PullRequestMapper {
    public PullRequestDto toDto(PullRequest pullRequest) {
        return new PullRequestDto(pullRequest.getId().value(), pullRequest.getCodeRepositoryId().value(),
                pullRequest.getExternalId(), pullRequest.getTitle(), pullRequest.getAuthor(),
                pullRequest.getStatus().name(), pullRequest.getUpdatedAt());
    }

    public PullRequest toDomain(PullRequestDto dto) {
        return new PullRequest(PullRequestId.from(dto.id()), CodeRepositoryId.from(dto.codeRepositoryId()),
                dto.externalId(), dto.title(), dto.author(), PullRequestStatus.valueOf(dto.status()), dto.updatedAt());
    }
}
