package com.prtracker.application.parser;

import com.prtracker.domain.enums.CodeRepositoryIdentifierType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CodeRepositoryIdentifierParserTest {
    private CodeRepositoryIdentifierParser parser;

    @BeforeEach
    public void init() {
        parser = new CodeRepositoryIdentifierParser();
    }

    @Test
    void parse_whenInputIsNull_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parser.parse(null));

        assertEquals("Repository identifier cannot be null or empty", exception.getMessage());
    }

    @Test
    void parse_whenInputIsEmpty_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> parser.parse(""));

        assertEquals("Repository identifier cannot be null or empty", exception.getMessage());
    }

    @Test
    void parse_whenInputIsInvalid_shouldThrowIllegalArgumentException() {
        String invalidUrl = "random-invalid-string";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(invalidUrl));

        assertEquals("Invalid repository identifier. Expected: owner/name, HTTPS URL, or SSH URL. Got: " + invalidUrl,
                exception.getMessage());
    }

    @Test
    void parse_whenInputIsValidUrl_shouldReturnParsedCodeRepository() {
        String url = "https://github.com/owner/repo";

        ParsedCodeRepository parsed = parser.parse(url);

        assertEquals(CodeRepositoryIdentifierType.HTTPS_URL, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }

    @Test
    void parse_whenInputIsValidSsh_shouldReturnParsedCodeRepository() {
        String validSsh = "git@github.com:owner/repo";

        ParsedCodeRepository parsed = parser.parse(validSsh);

        assertEquals(CodeRepositoryIdentifierType.SSH_URL, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }

    @Test
    void parse_whenInputIsValidOwnerName_shouldReturnParsedCodeRepository() {
        String validOwnerName = "owner/repo";

        ParsedCodeRepository parsed = parser.parse(validOwnerName);

        assertEquals(CodeRepositoryIdentifierType.OWNER_NAME, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }
}
