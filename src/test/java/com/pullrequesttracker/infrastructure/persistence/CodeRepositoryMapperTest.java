package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.infrastructure.persistence.dto.CodeRepositoryDto;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Optional;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static com.pullrequesttracker.testfixtures.infrastructure.persistence.CodeRepositoryDtoTestBuilder.aCodeRepositoryDto;
import static org.junit.jupiter.api.Assertions.*;

class CodeRepositoryMapperTest {
    private final CodeRepositoryMapper mapper = new CodeRepositoryMapper();

    @Test
    void toDomain_shouldMapValuesCorrectly() {
        CodeRepositoryDto dto = aCodeRepositoryDto().build();

        CodeRepository repo = mapper.toDomain(dto);

        assertEquals(CodeRepositoryId.from(dto.id()), repo.getId());
        assertEquals(new FullName(dto.owner(), dto.name()), repo.getFullName());
        assertEquals(Platform.valueOf(dto.platform()), repo.getPlatform());
        assertEquals(Optional.of(TokenId.from(dto.tokenId())), repo.getTokenId());
    }

    @Test
    void toDomain_whenTokenIdIsNull_shouldMapTokenIdToEmpty() {
        CodeRepositoryDto dto = aCodeRepositoryDto().withTokenId(null).build();

        assertTrue(mapper.toDomain(dto).getTokenId().isEmpty());
    }

    @Test
    void toDomain_whenLastCheckedAtIsSet_shouldMapLastCheckedAt() {
        Instant ts = Instant.parse("2026-05-13T10:00:00Z");
        CodeRepositoryDto dto = aCodeRepositoryDto().withLastCheckedAt(ts.toString()).build();

        assertEquals(Optional.of(ts), mapper.toDomain(dto).getLastCheckedAt());
    }

    @Test
    void toDomain_whenLastCheckedAtIsNull_shouldLeaveLastCheckedAtEmpty() {
        CodeRepositoryDto dto = aCodeRepositoryDto().withLastCheckedAt(null).build();

        assertTrue(mapper.toDomain(dto).getLastCheckedAt().isEmpty());
    }

    @Test
    void toDto_shouldMapValuesCorrectly() {
        CodeRepository repo = aCodeRepository().build();

        CodeRepositoryDto dto = mapper.toDto(repo);

        assertEquals(repo.getId().value(), dto.id());
        assertEquals(repo.getFullName().owner(), dto.owner());
        assertEquals(repo.getFullName().name(), dto.name());
        assertEquals(repo.getPlatform().name(), dto.platform());
        assertEquals(repo.getTokenId().map(TokenId::value).orElse(null), dto.tokenId());
    }

    @Test
    void toDto_whenTokenIdIsNull_shouldMapTokenIdToNull() {
        CodeRepository repo = aCodeRepository().withTokenId(null).build();

        assertNull(mapper.toDto(repo).tokenId());
    }

    @Test
    void toDto_whenLastCheckedAtIsSet_shouldMapLastCheckedAt() {
        Instant ts = Instant.parse("2026-05-13T10:00:00Z");
        CodeRepository repo = aCodeRepository().withLastCheckedAt(ts).build();

        assertEquals(ts.toString(), mapper.toDto(repo).lastCheckedAt());
    }

    @Test
    void toDto_whenLastCheckedAtIsNull_shouldMapLastCheckedAtToNull() {
        CodeRepository repo = aCodeRepository().withLastCheckedAt(null).build();

        assertNull(mapper.toDto(repo).lastCheckedAt());
    }
}
