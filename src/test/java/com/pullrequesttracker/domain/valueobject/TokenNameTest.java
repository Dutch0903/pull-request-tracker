package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenNameTest {
    @Test
    void from_whenValueIsValid_shouldCreateTokenName() {
        TokenName tokenName = TokenName.from("github-token");

        assertNotNull(tokenName);
        assertEquals("github-token", tokenName.toString());
    }

    @Test
    void from_whenValueIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> TokenName.from(null));
    }

    @Test
    void from_whenValueIsBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> TokenName.from("  "));
    }

    @Test
    void equals_whenNamesHaveSameValue_shouldBeEqual() {
        assertEquals(TokenName.from("github-token"), TokenName.from("github-token"));
    }

    @Test
    void equals_whenNamesHaveDifferentValues_shouldNotBeEqual() {
        assertNotEquals(TokenName.from("github"), TokenName.from("gitlab"));
    }
}
