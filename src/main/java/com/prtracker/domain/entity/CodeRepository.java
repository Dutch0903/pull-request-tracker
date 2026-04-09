package com.prtracker.domain.entity;

import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.FullName;
import com.prtracker.domain.valueobject.TokenId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeRepository {
    private CodeRepositoryId id;
    private FullName fullName;
    private CodeRepositoryStatus status;
    private TokenId tokenId;

    public CodeRepository(CodeRepositoryId id, FullName fullName, TokenId tokenId) {
        this.id = id;
        this.fullName = fullName;
        this.tokenId = tokenId;
        this.status = CodeRepositoryStatus.INACTIVE;
    }
}
