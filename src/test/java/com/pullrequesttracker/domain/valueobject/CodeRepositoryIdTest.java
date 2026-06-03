package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CodeRepositoryIdTest {
    @Test
    void from_whenValueIsValid_shouldCreateCodeRepositoryId() {
        UUID uuid = UUID.randomUUID();

        CodeRepositoryId id = CodeRepositoryId.from(uuid);

        assertNotNull(id);
        assertEquals(uuid, id.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> CodeRepositoryId.from(null));
    }

    @Test
    void equals_whenIdsHaveSameValue_shouldBeEqual() {
        UUID uuid = UUID.randomUUID();
        assertEquals(CodeRepositoryId.from(uuid), CodeRepositoryId.from(uuid));
    }

    @Test
    void equals_whenIdsHaveDifferentValues_shouldNotBeEqual() {
        assertNotEquals(CodeRepositoryId.create(), CodeRepositoryId.create());
    }

    @Test
    void create_whenCalledMultipleTimes_shouldGenerateUniqueValues() {
        assertNotEquals(CodeRepositoryId.create(), CodeRepositoryId.create());
    }
}
