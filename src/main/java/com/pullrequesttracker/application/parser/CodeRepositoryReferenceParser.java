package com.pullrequesttracker.application.parser;

import com.pullrequesttracker.domain.type.Platform;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class CodeRepositoryReferenceParser {

    private final Map<Platform, PlatformCodeRepositoryReferenceParser> parsers;

    public CodeRepositoryReferenceParser(List<PlatformCodeRepositoryReferenceParser> parsers) {
        this.parsers = parsers.stream()
                .collect(Collectors.toMap(PlatformCodeRepositoryReferenceParser::platform, Function.identity()));
    }

    public ParsedCodeRepositoryReference parse(String input, Platform platform) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Repository identifier cannot be null or empty");
        }

        PlatformCodeRepositoryReferenceParser parser = parsers.get(platform);
        if (parser == null) {
            throw new IllegalStateException("No parser registered for platform: " + platform);
        }

        return parser.parse(input);
    }
}
