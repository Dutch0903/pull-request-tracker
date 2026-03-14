package com.prtracker.domain.exceptions;

import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;

public class CodeRepositoryAlreadyExistsException extends RuntimeException {
    public CodeRepositoryAlreadyExistsException(CodeRepositoryIdentifier codeRepositoryIdentifier) {
        super("Code Repository with identifier " + codeRepositoryIdentifier.value() + " already exists");
    }
}
