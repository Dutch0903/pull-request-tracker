package com.prtracker.coderepository.application.parser;

import com.prtracker.coderepository.domain.model.CodeRepositoryReferenceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CodeRepositoryReferenceParserTest {
    private CodeRepositoryReferenceParser parser;

    @BeforeEach
    public void init() {
        parser = new CodeRepositoryReferenceParser();
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

        ParsedCodeRepositoryReference parsed = parser.parse(url);

        assertEquals(CodeRepositoryReferenceType.HTTPS_URL, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }

    @Test
    void parse_whenInputIsValidSsh_shouldReturnParsedCodeRepository() {
        String validSsh = "git@github.com:owner/repo";

        ParsedCodeRepositoryReference parsed = parser.parse(validSsh);

        assertEquals(CodeRepositoryReferenceType.SSH_URL, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }

    @Test
    void parse_whenInputIsValidOwnerName_shouldReturnParsedCodeRepository() {
        String validOwnerName = "owner/repo";

        ParsedCodeRepositoryReference parsed = parser.parse(validOwnerName);

        assertEquals(CodeRepositoryReferenceType.OWNER_NAME, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }
}
