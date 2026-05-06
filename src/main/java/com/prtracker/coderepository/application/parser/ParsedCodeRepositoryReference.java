package com.prtracker.coderepository.application.parser;

import com.prtracker.coderepository.domain.model.CodeRepositoryReferenceType;

public record ParsedCodeRepositoryReference(String owner, String name, CodeRepositoryReferenceType type) {
}
