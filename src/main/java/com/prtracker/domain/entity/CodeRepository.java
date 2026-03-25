package com.prtracker.domain.entity;

import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeRepository {
    private CodeRepositoryIdentifier identifier;
    private String owner;
    private String name;
    private CodeRepositoryStatus status;

    public CodeRepository(CodeRepositoryIdentifier identifier, String owner, String name) {
        this.identifier = identifier;
        this.owner = owner;
        this.name = name;
        this.status = CodeRepositoryStatus.INACTIVE;
    }
}
