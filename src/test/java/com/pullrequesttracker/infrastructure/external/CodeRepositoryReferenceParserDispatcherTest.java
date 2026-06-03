package com.pullrequesttracker.infrastructure.external;

import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.type.CodeRepositoryReferenceType;
import com.pullrequesttracker.domain.type.Platform;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CodeRepositoryReferenceParserDispatcherTest {
    private PlatformReferenceParser githubParser;
    private CodeRepositoryReferenceParserDispatcher dispatcher;

    @BeforeEach
    void setup() {
        githubParser = mock(PlatformReferenceParser.class);
        when(githubParser.platform()).thenReturn(Platform.GITHUB);
        dispatcher = new CodeRepositoryReferenceParserDispatcher(List.of(githubParser));
    }

    @Test
    void parse_whenInputIsNull_shouldThrowIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> dispatcher.parse(null, Platform.GITHUB));
        assertEquals("Repository identifier cannot be null or empty", ex.getMessage());
    }

    @Test
    void parse_whenInputIsBlank_shouldThrowIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> dispatcher.parse("", Platform.GITHUB));
        assertEquals("Repository identifier cannot be null or empty", ex.getMessage());
    }

    @Test
    void parse_whenPlatformHasNoRegisteredParser_shouldThrowIllegalStateException() {
        assertThrows(IllegalStateException.class,
                () -> dispatcher.parse("owner/repo", mock(Platform.class)));
    }

    @Test
    void parse_whenPlatformMatches_shouldDelegateToStrategy() {
        ParsedCodeRepositoryReference expected = new ParsedCodeRepositoryReference("owner", "repo", CodeRepositoryReferenceType.OWNER_NAME);
        when(githubParser.parse("owner/repo")).thenReturn(expected);

        ParsedCodeRepositoryReference result = dispatcher.parse("owner/repo", Platform.GITHUB);

        assertEquals(expected, result);
        verify(githubParser).parse("owner/repo");
    }
}
