package com.prtracker.domain.entity;

import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import com.prtracker.domain.valueobject.TokenId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeRepository {
    private CodeRepositoryIdentifier identifier;
    private String owner;
    private String name;
    private CodeRepositoryStatus status;
    private TokenId tokenId;

    public CodeRepository(CodeRepositoryIdentifier identifier, String owner, String name, TokenId tokenId) {
        this.identifier = identifier;
        this.owner = owner;
        this.name = name;
        this.tokenId = tokenId;
        this.status = CodeRepositoryStatus.INACTIVE;
    }
}
