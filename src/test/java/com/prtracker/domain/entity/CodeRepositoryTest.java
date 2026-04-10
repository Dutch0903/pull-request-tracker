package com.prtracker.domain.entity;

import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.FullName;
import com.prtracker.domain.valueobject.TokenId;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CodeRepositoryTest {
    @Test
    void constructor_whenNoStatusIsGiven_shouldSetTheStatusToInactive() {
        CodeRepository codeRepository = new CodeRepository(
                CodeRepositoryId.create(),
                new FullName("owner", "name"),
                TokenId.create()
        );

        assertEquals(CodeRepositoryStatus.INACTIVE, codeRepository.getStatus());
    }
}
