package com.prtracker.testfixtures;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.FullName;
import com.prtracker.domain.valueobject.TokenId;

public class CodeRepositoryTestBuilder {
    private CodeRepositoryId codeRepositoryId = CodeRepositoryId.create();
    private FullName fullName = new FullName("account", "repo");
    private CodeRepositoryStatus status = CodeRepositoryStatus.INACTIVE;
    private TokenId tokenId = TokenId.create();

    public static CodeRepositoryTestBuilder aCodeRepository() {
        return new CodeRepositoryTestBuilder();
    }

    public static CodeRepositoryTestBuilder copyOf(CodeRepository codeRepository) {
        return aCodeRepository().withCodeRepositoryId(codeRepository.getId()).withFullName(codeRepository.getFullName())
                .withStatus(codeRepository.getStatus()).withTokenId(codeRepository.getTokenId());
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

    public CodeRepository build() {
        return new CodeRepository(codeRepositoryId, fullName, status, tokenId);
    }
}
