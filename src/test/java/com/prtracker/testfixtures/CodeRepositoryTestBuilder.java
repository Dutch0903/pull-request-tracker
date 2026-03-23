package com.prtracker.testfixtures;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;

public class CodeRepositoryTestBuilder {
    private CodeRepositoryIdentifier identifier = new CodeRepositoryIdentifier(
            "account/repo"
    );
    private String owner = "account";
    private String name = "repo";
    private String url = "https://github.com/account/repo";
    private CodeRepositoryStatus status = CodeRepositoryStatus.INACTIVE;

    public static CodeRepositoryTestBuilder aCodeRepository() {
        return new CodeRepositoryTestBuilder();
    }

    public static CodeRepositoryTestBuilder copyOf(CodeRepository codeRepository) {
        return aCodeRepository()
                .withIdentifier(codeRepository.getIdentifier())
                .withName(codeRepository.getName())
                .withOwner(codeRepository.getOwner())
                .withUrl(codeRepository.getUrl())
                .withStatus(codeRepository.getStatus());
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
        this.name  =name;
        return this;
    }

    public CodeRepositoryTestBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public CodeRepositoryTestBuilder withStatus(CodeRepositoryStatus status) {
        this.status = status;
        return this;
    }

    public CodeRepository build() {
        return new CodeRepository(
                identifier,
                owner,
                name,
                url,
                status
        );
    }
}
