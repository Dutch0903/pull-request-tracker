package com.prtracker.domain.exceptions;

import com.prtracker.domain.valueobject.FullName;

public class CodeRepositoryAlreadyExistsException extends RuntimeException {
    public CodeRepositoryAlreadyExistsException(FullName fullName) {
        super("Code Repository " + fullName + " already exists");
    }
}
