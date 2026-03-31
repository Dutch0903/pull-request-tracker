package com.prtracker.testfixtures;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import com.prtracker.domain.valueobject.TokenId;

public class CodeRepositoryTestBuilder {
    private CodeRepositoryIdentifier identifier = new CodeRepositoryIdentifier("account/repo");
    private String owner = "account";
    private String name = "repo";
    private CodeRepositoryStatus status = CodeRepositoryStatus.INACTIVE;
    private TokenId tokenId = TokenId.create();

    public static CodeRepositoryTestBuilder aCodeRepository() {
        return new CodeRepositoryTestBuilder();
    }

    public static CodeRepositoryTestBuilder copyOf(CodeRepository codeRepository) {
        return aCodeRepository().withIdentifier(codeRepository.getIdentifier()).withName(codeRepository.getName())
                .withOwner(codeRepository.getOwner()).withStatus(codeRepository.getStatus()).withTokenId(codeRepository.getTokenId());
    };

    public CodeRepositoryTestBuilder withIdentifier(CodeRepositoryIdentifier identifier) {
        this.identifier = identifier;
        return this;
    }

    public CodeRepositoryTestBuilder withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public CodeRepositoryTestBuilder withName(String name) {
        this.name = name;
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
        return new CodeRepository(identifier, owner, name, status, tokenId);
    }
}
