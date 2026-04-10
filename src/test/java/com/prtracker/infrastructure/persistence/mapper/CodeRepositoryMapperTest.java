package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.FullName;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.infrastructure.persistence.dto.CodeRepositoryDto;
import org.junit.jupiter.api.Test;

import static com.prtracker.testfixtures.infrastructure.persistence.dto.CodeRepositoryDtoTestBuilder.aCodeRepositoryDto;
import static com.prtracker.testfixtures.domain.entity.CodeRepositoryTestBuilder.aCodeRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CodeRepositoryMapperTest {
    @Test
    void toDomain_whenCalled_shouldMapValuesCorrectly() {
        CodeRepositoryMapper mapper = new CodeRepositoryMapper();

        CodeRepositoryDto dto = aCodeRepositoryDto().build();

        CodeRepository codeRepository = mapper.toDomain(dto);

        assertEquals(CodeRepositoryId.from(dto.id()), codeRepository.getId());
        assertEquals(new FullName(dto.owner(),  dto.name()), codeRepository.getFullName());
        assertEquals(CodeRepositoryStatus.valueOf(dto.status()),  codeRepository.getStatus());
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
}
