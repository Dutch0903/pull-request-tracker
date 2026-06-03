package com.pullrequesttracker.application.parser;

import com.pullrequesttracker.domain.type.Platform;

public interface PlatformCodeRepositoryReferenceParser {
    Platform platform();
    ParsedCodeRepositoryReference parse(String input);
}
