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
    private String url;
    private CodeRepositoryStatus status;
    private String accessToken;

    public CodeRepository(
            CodeRepositoryIdentifier identifier,
            String owner,
            String name,
            String url,
            String accessToken
    ) {
        this.identifier = identifier;
        this.owner = owner;
        this.name = name;
        this.url = url;
        this.accessToken = accessToken;
        this.status = CodeRepositoryStatus.INACTIVE;
    }
}
