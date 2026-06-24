package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenValueTest {
    @Test
    void from_whenValueIsValid_shouldCreateTokenValue() {
        TokenValue tokenValue = TokenValue.from("ghp_abc123");

        assertNotNull(tokenValue);
        assertEquals("ghp_abc123", tokenValue.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> TokenValue.from(null));
    }

    @Test
    void from_whenValueIsBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> TokenValue.from("  "));
    }

    @Test
    void toString_shouldReturnValue() {
        assertEquals("ghp_abc123", TokenValue.from("ghp_abc123").toString());
    }

    @Test
    void equals_whenValuesHaveSameContent_shouldBeEqual() {
        assertEquals(TokenValue.from("ghp_abc123"), TokenValue.from("ghp_abc123"));
    }

    @Test
    void equals_whenValuesHaveDifferentContent_shouldNotBeEqual() {
        assertNotEquals(TokenValue.from("ghp_abc123"), TokenValue.from("ghp_xyz789"));
    }
}
