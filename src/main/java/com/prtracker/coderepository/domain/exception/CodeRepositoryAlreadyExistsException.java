package com.prtracker.coderepository.domain.exception;

import com.prtracker.coderepository.domain.model.FullName;

public class CodeRepositoryAlreadyExistsException extends RuntimeException {
    public CodeRepositoryAlreadyExistsException(FullName fullName) {
        super("Code Repository " + fullName + " already exists");
    }
}
