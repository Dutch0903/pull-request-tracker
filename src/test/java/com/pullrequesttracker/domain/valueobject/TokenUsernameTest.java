package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TokenUsernameTest {
    @Test
    void from_whenValueIsValid_shouldCreateTokenUsername() {
        TokenUsername tokenUsername = TokenUsername.from("octocat");

        assertNotNull(tokenUsername);
        assertEquals("octocat", tokenUsername.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> TokenUsername.from(null));
    }

    @Test
    void from_whenValueIsBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> TokenUsername.from("  "));
    }

    @Test
    void toString_shouldReturnValue() {
        assertEquals("octocat", TokenUsername.from("octocat").toString());
    }

    @Test
    void equals_whenUsernamesHaveSameValue_shouldBeEqual() {
        assertEquals(TokenUsername.from("octocat"), TokenUsername.from("octocat"));
    }

    @Test
    void equals_whenUsernamesHaveDifferentValues_shouldNotBeEqual() {
        assertNotEquals(TokenUsername.from("octocat"), TokenUsername.from("torvalds"));
    }
}
