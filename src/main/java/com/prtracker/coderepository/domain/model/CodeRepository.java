package com.prtracker.coderepository.domain.model;

import com.prtracker.shared.kernel.CodeRepositoryId;
import com.prtracker.shared.kernel.TokenId;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class CodeRepository {
    private CodeRepositoryId id;
    private FullName fullName;
    private CodeRepositoryStatus status;
    private TokenId tokenId;
    private Instant lastCheckedAt;

    public CodeRepository(CodeRepositoryId id, FullName fullName, TokenId tokenId) {
        this.id = id;
        this.fullName = fullName;
        this.tokenId = tokenId;
        this.status = CodeRepositoryStatus.INACTIVE;
        this.lastCheckedAt = null;
    }

    public void recordChecked(Instant checkedAt) {
        this.lastCheckedAt = checkedAt;
    }
}
