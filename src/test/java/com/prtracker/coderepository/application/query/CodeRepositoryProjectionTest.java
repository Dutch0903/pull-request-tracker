package com.prtracker.coderepository.application.query;

import com.prtracker.coderepository.domain.model.CodeRepository;
import org.junit.jupiter.api.Test;

import static com.prtracker.testfixtures.coderepository.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeRepositoryProjectionTest {
    @Test
    void from_whenCalled_shouldMapValuesCorrectly() {
        CodeRepository codeRepository = aCodeRepository().build();

        CodeRepositoryProjection projection = CodeRepositoryProjection.from(codeRepository);

        assertEquals(codeRepository.getId().value(), projection.id());
        assertEquals(codeRepository.getFullName().owner(), projection.owner());
        assertEquals(codeRepository.getFullName().name(), projection.name());
        assertEquals(codeRepository.getStatus(), projection.status());
    }
}
