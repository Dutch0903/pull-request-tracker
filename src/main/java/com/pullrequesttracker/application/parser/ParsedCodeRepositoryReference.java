package com.pullrequesttracker.application.parser;

import com.pullrequesttracker.domain.type.CodeRepositoryReferenceType;

public record ParsedCodeRepositoryReference(String owner, String name, CodeRepositoryReferenceType type) {
}
