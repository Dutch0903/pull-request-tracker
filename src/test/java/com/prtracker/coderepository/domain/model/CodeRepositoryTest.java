package com.prtracker.coderepository.domain.model;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.CodeRepositoryStatus;
import com.prtracker.coderepository.domain.model.FullName;
import com.prtracker.shared.kernel.CodeRepositoryId;
import com.prtracker.shared.kernel.TokenId;
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
