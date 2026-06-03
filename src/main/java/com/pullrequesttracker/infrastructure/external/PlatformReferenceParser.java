package com.pullrequesttracker.infrastructure.external;

import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.type.Platform;

public interface PlatformReferenceParser {
    Platform platform();
    ParsedCodeRepositoryReference parse(String input);
}
