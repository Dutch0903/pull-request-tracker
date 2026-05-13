package com.prtracker.testfixtures.coderepository.domain.model;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.CodeRepositoryStatus;
import com.prtracker.coderepository.domain.model.FullName;
import com.prtracker.shared.kernel.CodeRepositoryId;
import com.prtracker.shared.kernel.TokenId;

import java.time.Instant;

public class CodeRepositoryTestBuilder {
    private CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
    private FullName fullName = new FullName("account", "repo");
    private CodeRepositoryStatus status = CodeRepositoryStatus.INACTIVE;
    private TokenId tokenId = TokenId.create();
    private Instant lastCheckedAt = null;

    public static CodeRepositoryTestBuilder aCodeRepository() {
        return new CodeRepositoryTestBuilder();
    }

    public static CodeRepositoryTestBuilder copyOf(CodeRepository codeRepository) {
        return aCodeRepository().withCodeRepositoryId(codeRepository.getId()).withFullName(codeRepository.getFullName())
                .withStatus(codeRepository.getStatus()).withTokenId(codeRepository.getTokenId())
                .withLastCheckedAt(codeRepository.getLastCheckedAt());
    }

    public CodeRepositoryTestBuilder withCodeRepositoryId(CodeRepositoryId codeRepositoryId) {
        this.codeRepositoryId = codeRepositoryId;
        return this;
    }

    public CodeRepositoryTestBuilder withFullName(FullName fullName) {
        this.fullName = fullName;
        return this;
    }

    public CodeRepositoryTestBuilder withStatus(CodeRepositoryStatus status) {
        this.status = status;
        return this;
    }

    public CodeRepositoryTestBuilder withTokenId(TokenId tokenId) {
        this.tokenId = tokenId;
        return this;
    }

    public CodeRepositoryTestBuilder withLastCheckedAt(Instant lastCheckedAt) {
        this.lastCheckedAt = lastCheckedAt;
        return this;
    }

    public CodeRepository build() {
        return new CodeRepository(codeRepositoryId, fullName, status, tokenId, lastCheckedAt);
    }
}
