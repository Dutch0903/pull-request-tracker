package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.parser.CodeRepositoryReferenceParser;
import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;
import org.jspecify.annotations.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCodeRepository {
    private final CodeRepositoryReferenceParser parser;
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final TokenRepository tokenRepository;

    public void execute(String repositoryReference, Platform platform, @Nullable TokenId tokenId) {
        ParsedCodeRepositoryReference parsed = parser.parse(repositoryReference, platform);
        FullName fullName = new FullName(parsed.owner(), parsed.name());

        if (codeRepositoryRepository.exists(fullName)) {
            throw new IllegalStateException("Repository already exists: " + fullName);
        }

        if (tokenId != null && tokenRepository.findById(tokenId).isEmpty()) {
            throw new IllegalStateException("Token not found: " + tokenId);
        }

        codeRepositoryRepository.save(new CodeRepository(CodeRepositoryId.create(), fullName, platform, tokenId));
    }
}
