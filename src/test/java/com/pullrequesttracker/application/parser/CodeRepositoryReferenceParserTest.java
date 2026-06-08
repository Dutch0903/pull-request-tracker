package com.pullrequesttracker.application.parser;

import com.pullrequesttracker.domain.type.CodeRepositoryReferenceType;
import com.pullrequesttracker.domain.type.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CodeRepositoryReferenceParserTest {
    private PlatformCodeRepositoryReferenceParser githubParser;
    private CodeRepositoryReferenceParser parser;

    @BeforeEach
    void setup() {
        githubParser = mock(PlatformCodeRepositoryReferenceParser.class);
        when(githubParser.platform()).thenReturn(Platform.GITHUB);
        parser = new CodeRepositoryReferenceParser(List.of(githubParser));
    }

    @Test
    void parse_whenInputIsNull_shouldThrowIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> parser.parse(null, Platform.GITHUB));
        assertEquals("Repository identifier cannot be null or empty", ex.getMessage());
    }

    @Test
    void parse_whenInputIsBlank_shouldThrowIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> parser.parse("", Platform.GITHUB));
        assertEquals("Repository identifier cannot be null or empty", ex.getMessage());
    }

    @Test
    void parse_whenPlatformHasNoRegisteredParser_shouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> parser.parse("owner/repo", mock(Platform.class)));
    }

    @Test
    void parse_whenPlatformMatches_shouldDelegateToStrategy() {
        ParsedCodeRepositoryReference expected = new ParsedCodeRepositoryReference("owner", "repo",
                CodeRepositoryReferenceType.OWNER_NAME);
        when(githubParser.parse("owner/repo")).thenReturn(expected);

        ParsedCodeRepositoryReference result = parser.parse("owner/repo", Platform.GITHUB);

        assertEquals(expected, result);
        verify(githubParser).parse("owner/repo");
    }
}
