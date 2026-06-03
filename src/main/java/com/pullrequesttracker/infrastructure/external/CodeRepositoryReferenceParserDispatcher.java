package com.pullrequesttracker.infrastructure.external;

import com.pullrequesttracker.application.parser.CodeRepositoryReferenceParser;
import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.type.Platform;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CodeRepositoryReferenceParserDispatcher implements CodeRepositoryReferenceParser {

    private final Map<Platform, PlatformReferenceParser> parsers;

    public CodeRepositoryReferenceParserDispatcher(List<PlatformReferenceParser> parsers) {
        this.parsers = parsers.stream()
                .collect(Collectors.toMap(PlatformReferenceParser::platform, Function.identity()));
    }

    @Override
    public ParsedCodeRepositoryReference parse(String input, Platform platform) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Repository identifier cannot be null or empty");
        }

        PlatformReferenceParser parser = parsers.get(platform);
        if (parser == null) {
            throw new IllegalStateException("No parser registered for platform: " + platform);
        }

        return parser.parse(input);
    }
}
