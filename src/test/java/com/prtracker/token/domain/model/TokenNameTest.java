package com.prtracker.token.domain.model;

import com.prtracker.token.domain.exception.InvalidTokenNameException;
import com.prtracker.token.domain.model.TokenName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TokenNameTest {
    @Test
    void from_whenValueIsValid_shouldCreateTokenName() {
        String validName = "github-token";

        TokenName tokenName = TokenName.from(validName);

        assertNotNull(tokenName);
        assertEquals(validName, tokenName.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowInvalidTokenNameException() {
        InvalidTokenNameException exception = assertThrows(InvalidTokenNameException.class, () -> TokenName.from(null));

        assertEquals("Invalid token name: Token name cannot be empty", exception.getMessage());
    }

    @Test
    void from_whenValueIsEmpty_shouldThrowInvalidTokenNameException() {
        InvalidTokenNameException exception = assertThrows(InvalidTokenNameException.class, () -> TokenName.from(""));

        assertEquals("Invalid token name: Token name cannot be empty", exception.getMessage());
    }

    @Test
    void from_whenValueIsBlank_shouldThrowInvalidTokenException() {
        InvalidTokenNameException exception = assertThrows(InvalidTokenNameException.class,
                () -> TokenName.from("    "));

        assertEquals("Invalid token name: Token name cannot be empty", exception.getMessage());
    }

    @Test
    void equals_whenTokenNamesHaveSameValue_shouldBeEqual() {
        TokenName tokenName1 = TokenName.from("github-token");
        TokenName tokenName2 = TokenName.from("github-token");

        assertEquals(tokenName1, tokenName2);
        assertEquals(tokenName1.hashCode(), tokenName2.hashCode());
    }

    @Test
    void equals_whenTokenNamesHaveDifferentValues_shouldNotBeEqual() {
        TokenName tokenName1 = TokenName.from("github");
        TokenName tokenName2 = TokenName.from("gitlab");

        assertNotEquals(tokenName1, tokenName2);
    }
}
