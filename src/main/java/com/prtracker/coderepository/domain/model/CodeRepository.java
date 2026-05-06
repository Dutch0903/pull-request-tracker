package com.prtracker.coderepository.domain.model;

import com.prtracker.shared.kernel.CodeRepositoryId;
import com.prtracker.shared.kernel.TokenId;
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
