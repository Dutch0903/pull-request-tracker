package com.prtracker.coderepository.adapter.out.persistence;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.CodeRepositoryStatus;
import com.prtracker.coderepository.domain.model.FullName;
import com.prtracker.shared.kernel.CodeRepositoryId;
import com.prtracker.shared.kernel.TokenId;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.prtracker.testfixtures.coderepository.adapter.out.persistence.CodeRepositoryDtoTestBuilder.aCodeRepositoryDto;
import static com.prtracker.testfixtures.coderepository.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CodeRepositoryMapperTest {
    @Test
    void toDomain_whenCalled_shouldMapValuesCorrectly() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();

        CodeRepositoryDto dto = aCodeRepositoryDto().build();

        CodeRepository codeRepository = mapper.toDomain(dto);

        assertEquals(CodeRepositoryId.from(dto.id()), codeRepository.getId());
        assertEquals(new FullName(dto.owner(), dto.name()), codeRepository.getFullName());
        assertEquals(CodeRepositoryStatus.valueOf(dto.status()), codeRepository.getStatus());
        assertEquals(TokenId.from(dto.tokenId()), codeRepository.getTokenId());
    }

    @Test
    void toDomain_whenTokenIdIsNull_shouldMapTokenIdToNull() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();

        CodeRepositoryDto dto = aCodeRepositoryDto().withTokenId(null).build();

        CodeRepository codeRepository = mapper.toDomain(dto);

        assertNull(codeRepository.getTokenId());
    }

    @Test
    void toDto_whenCalled_shouldMapValuesCorrectly() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();

        CodeRepository codeRepository = aCodeRepository().build();

        CodeRepositoryDto dto = mapper.toDto(codeRepository);

        assertEquals(codeRepository.getId().value(), dto.id());
        assertEquals(codeRepository.getFullName().owner(), dto.owner());
        assertEquals(codeRepository.getFullName().name(), dto.name());
        assertEquals(codeRepository.getStatus().toString(), dto.status());
        assertEquals(codeRepository.getTokenId().value(), dto.tokenId());
    }

    @Test
    void toDto_whenTokenIdIsNull_shouldMapTokenIdIsNull() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();

        CodeRepository codeRepository = aCodeRepository().withTokenId(null).build();

        CodeRepositoryDto dto = mapper.toDto(codeRepository);

        assertEquals(codeRepository.getId().value(), dto.id());
        assertNull(dto.tokenId());
    }

    @Test
    void toDomain_whenLastCheckedAtIsSet_shouldMapLastCheckedAt() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();
        Instant lastCheckedAt = Instant.parse("2026-05-13T10:00:00Z");

        CodeRepositoryDto dto = aCodeRepositoryDto().withLastCheckedAt(lastCheckedAt.toString()).build();

        CodeRepository codeRepository = mapper.toDomain(dto);

        assertEquals(lastCheckedAt, codeRepository.getLastCheckedAt());
    }

    @Test
    void toDomain_whenLastCheckedAtIsNull_shouldMapLastCheckedAtToNull() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();

        CodeRepositoryDto dto = aCodeRepositoryDto().withLastCheckedAt(null).build();

        CodeRepository codeRepository = mapper.toDomain(dto);

        assertNull(codeRepository.getLastCheckedAt());
    }

    @Test
    void toDto_whenLastCheckedAtIsSet_shouldMapLastCheckedAt() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();
        Instant lastCheckedAt = Instant.parse("2026-05-13T10:00:00Z");

        CodeRepository codeRepository = aCodeRepository().withLastCheckedAt(lastCheckedAt).build();

        CodeRepositoryDto dto = mapper.toDto(codeRepository);

        assertEquals(lastCheckedAt.toString(), dto.lastCheckedAt());
    }

    @Test
    void toDto_whenLastCheckedAtIsNull_shouldMapLastCheckedAtToNull() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();

        CodeRepository codeRepository = aCodeRepository().withLastCheckedAt(null).build();

        CodeRepositoryDto dto = mapper.toDto(codeRepository);

        assertNull(dto.lastCheckedAt());
    }
}
