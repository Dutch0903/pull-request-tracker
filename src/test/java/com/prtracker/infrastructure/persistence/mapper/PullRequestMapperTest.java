package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.enums.PullRequestStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.PullRequestId;
import com.prtracker.infrastructure.persistence.dto.PullRequestDto;
import org.junit.jupiter.api.Test;

import static com.prtracker.testfixtures.domain.entity.PullRequestTestBuilder.aPullRequest;
import static com.prtracker.testfixtures.infrastructure.persistence.dto.PullRequestDtoTestBuilder.aPullRequestDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PullRequestMapperTest {
    @Test
    void toDto_whenCalled_shouldMapValuesCorrectly() {
        PullRequestMapper mapper = new PullRequestMapper();

        PullRequest pullRequest = aPullRequest().build();

        PullRequestDto dto = mapper.toDto(pullRequest);

        assertEquals(pullRequest.getId().value(), dto.id());
        assertEquals(pullRequest.getCodeRepositoryId().value(), dto.codeRepositoryId());
        assertEquals(pullRequest.getTitle(), dto.title());
        assertEquals(pullRequest.getAuthor(), dto.author());
        assertEquals(pullRequest.getStatus().name(), dto.status());
        assertEquals(pullRequest.getUpdatedAt(), dto.updatedAt());
    }

    @Test
    void toDomain_whenCalled_shouldMapValuesCorrectly() {
        PullRequestMapper mapper = new PullRequestMapper();

        PullRequestDto dto = aPullRequestDto().build();

        PullRequest pullRequest = mapper.toDomain(dto);

        assertEquals(PullRequestId.from(dto.id()), pullRequest.getId());
        assertEquals(CodeRepositoryId.from(dto.codeRepositoryId()), pullRequest.getCodeRepositoryId());
        assertEquals(dto.title(), pullRequest.getTitle());
        assertEquals(dto.author(), pullRequest.getAuthor());
        assertEquals(PullRequestStatus.valueOf(dto.status()), pullRequest.getStatus());
        assertEquals(dto.updatedAt(), pullRequest.getUpdatedAt());
    }
}
