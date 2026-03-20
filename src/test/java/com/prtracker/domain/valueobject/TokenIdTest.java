package com.prtracker.domain.valueobject;

import com.prtracker.domain.exceptions.InvalidTokenIdException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TokenIdTest {

    @Test
    void from_whenValueIsValid_shouldCreateTokenId() {
        UUID id = UUID.randomUUID();

        TokenId tokenId = TokenId.from(id);

        assertNotNull(tokenId);
        assertEquals(id, tokenId.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowInvalidTokenIdException() {
        InvalidTokenIdException exception = assertThrows(
                InvalidTokenIdException.class,
                () -> TokenId.from(null)
        );

        assertEquals("Invalid token ID: Token ID cannot be null", exception.getMessage());
    }

    @Test
    void equals_whenTokenIdsHaveSameValue_shouldBeEqual() {
        UUID id = UUID.randomUUID();
        TokenId tokenId1 = TokenId.from(id);
        TokenId tokenId2 = TokenId.from(id);

        assertEquals(tokenId1, tokenId2);
        assertEquals(tokenId1.hashCode(), tokenId2.hashCode());
    }

    @Test
    void equals_whenTokenIdsHaveDifferentValues_shouldNotBeEqual() {
        TokenId tokenId1 = TokenId.from(UUID.fromString("05b8750a-7f28-4671-9faf-c12a515ae22c"));
        TokenId tokenId2 = TokenId.from(UUID.fromString("01166ad2-a2d8-4201-ba8f-356681085bfd"));

        assertNotEquals(tokenId1, tokenId2);
    }
    @Test
    void create_whenCalledMultipleTimes_shouldGenerateUniqueIds() {
        TokenId tokenId1 = TokenId.create();
        TokenId tokenId2 = TokenId.create();

        assertNotEquals(tokenId1, tokenId2);
        assertNotEquals(tokenId1.value(), tokenId2.value());
    }
}
