package com.prtracker.application.parser;

import com.prtracker.domain.enums.CodeRepositoryReferenceType;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;

public record ParsedCodeRepositoryReference(String owner, String name, CodeRepositoryReferenceType type) {
    public CodeRepositoryIdentifier getIdentifier() {
        return CodeRepositoryIdentifier.from(owner, name);
    }
}
