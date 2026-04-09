package com.prtracker.application.parser;

import com.prtracker.domain.enums.CodeRepositoryReferenceType;

public record ParsedCodeRepositoryReference(String owner, String name, CodeRepositoryReferenceType type) {
}
