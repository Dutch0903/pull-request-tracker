package com.prtracker.domain.valueobject;

import com.prtracker.domain.exceptions.InvalidCodeRepositoryIdException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CodeRepositoryIdTest {
    @Test
    void from_whenValueIsValid_shouldCreateCodeRepositoryId() {
        UUID uuid = UUID.randomUUID();

        CodeRepositoryId codeRepositoryId = CodeRepositoryId.from(uuid);

        assertNotNull(codeRepositoryId);
        assertEquals(uuid, codeRepositoryId.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowInvalidCodeRepositoryIdException() {
        InvalidCodeRepositoryIdException exception = assertThrows(InvalidCodeRepositoryIdException.class, () -> CodeRepositoryId.from(null));

        assertEquals("Invalid code repository ID: Code repository ID cannot be null", exception.getMessage());
    }

    @Test
    void equals_whenCodeRepositoryIdsHaveSameValue_shouldBeEqual() {
        UUID uuid = UUID.randomUUID();
        CodeRepositoryId codeRepositoryId1 = CodeRepositoryId.from(uuid);
        CodeRepositoryId codeRepositoryId2 = CodeRepositoryId.from(uuid);

        assertEquals(codeRepositoryId1, codeRepositoryId2);
        assertEquals(codeRepositoryId1.hashCode(), codeRepositoryId1.hashCode());
    }

    @Test
    void equals_whenCodeRepositoryIdsHaveDifferentValue_shouldNotBeEqual() {
        CodeRepositoryId codeRepositoryId1 = CodeRepositoryId.from(UUID.randomUUID());
        CodeRepositoryId codeRepositoryId2 = CodeRepositoryId.from(UUID.randomUUID());

        assertNotEquals(codeRepositoryId1, codeRepositoryId2);
    }

    @Test
    void create_whenCalledMultipleTimes_shouldGenerateUniqueValues() {
        CodeRepositoryId codeRepositoryId1 = CodeRepositoryId.create();
        CodeRepositoryId codeRepositoryId2 = CodeRepositoryId.create();

        assertNotEquals(codeRepositoryId1, codeRepositoryId2);
        assertNotEquals(codeRepositoryId1.value(), codeRepositoryId2.value());
    }
}
