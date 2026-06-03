package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TokenIdTest {
    @Test
    void from_whenValueIsValid_shouldCreateTokenId() {
        UUID uuid = UUID.randomUUID();

        TokenId id = TokenId.from(uuid);

        assertNotNull(id);
        assertEquals(uuid, id.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> TokenId.from(null));
    }

    @Test
    void equals_whenIdsHaveSameValue_shouldBeEqual() {
        UUID uuid = UUID.randomUUID();
        assertEquals(TokenId.from(uuid), TokenId.from(uuid));
    }

    @Test
    void equals_whenIdsHaveDifferentValues_shouldNotBeEqual() {
        assertNotEquals(TokenId.create(), TokenId.create());
    }

    @Test
    void create_whenCalledMultipleTimes_shouldGenerateUniqueIds() {
        assertNotEquals(TokenId.create(), TokenId.create());
    }
}
