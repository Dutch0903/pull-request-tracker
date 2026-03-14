package com.prtracker.application.parser;

import com.prtracker.domain.enums.CodeRepositoryIdentifierType;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;

public record ParsedCodeRepository(String owner, String name, CodeRepositoryIdentifierType type) {
    public CodeRepositoryIdentifier getIdentifier() {
        return CodeRepositoryIdentifier.from(owner, name);
    }
}
