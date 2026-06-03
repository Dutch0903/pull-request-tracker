package com.pullrequesttracker.application.parser;

import com.pullrequesttracker.domain.type.Platform;

public interface CodeRepositoryReferenceParser {
    ParsedCodeRepositoryReference parse(String input, Platform platform);
}
