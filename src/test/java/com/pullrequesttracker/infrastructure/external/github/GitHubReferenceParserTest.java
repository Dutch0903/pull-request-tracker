package com.pullrequesttracker.infrastructure.external.github;

import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.type.CodeRepositoryReferenceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GitHubReferenceParserTest {
    private GitHubReferenceParser parser;

    @BeforeEach
    void init() {
        parser = new GitHubReferenceParser();
    }

    @Test
    void parse_whenInputIsInvalid_shouldThrowIllegalArgumentException() {
        String invalid = "random-invalid-string";
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> parser.parse(invalid));
        assertTrue(ex.getMessage().contains(invalid));
    }

    @Test
    void parse_whenInputIsHttpsUrl_shouldReturnParsedReference() {
        ParsedCodeRepositoryReference parsed = parser.parse("https://github.com/owner/repo");
        assertEquals(CodeRepositoryReferenceType.HTTPS_URL, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }

    @Test
    void parse_whenInputIsSshUrl_shouldReturnParsedReference() {
        ParsedCodeRepositoryReference parsed = parser.parse("git@github.com:owner/repo");
        assertEquals(CodeRepositoryReferenceType.SSH_URL, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }

    @Test
    void parse_whenInputIsOwnerName_shouldReturnParsedReference() {
        ParsedCodeRepositoryReference parsed = parser.parse("owner/repo");
        assertEquals(CodeRepositoryReferenceType.OWNER_NAME, parsed.type());
        assertEquals("owner", parsed.owner());
        assertEquals("repo", parsed.name());
    }
}
