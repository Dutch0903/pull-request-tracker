package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.parser.CodeRepositoryReferenceParser;
import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.RepositoryAccess;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCodeRepository {
    private final CodeRepositoryReferenceParser parser;
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final TokenRepository tokenRepository;

    public void execute(String repositoryReference, Platform platform, RepositoryAccess access) {
        ParsedCodeRepositoryReference parsed = parser.parse(repositoryReference, platform);
        FullName fullName = new FullName(parsed.owner(), parsed.name());

        if (codeRepositoryRepository.exists(fullName)) {
            throw new IllegalStateException("Repository already exists: " + fullName);
        }

        if (access instanceof RepositoryAccess.Authenticated a && tokenRepository.findById(a.tokenId()).isEmpty()) {
            throw new IllegalStateException("Token not found: " + a.tokenId());
        }

        codeRepositoryRepository.save(new CodeRepository(CodeRepositoryId.create(), fullName, platform, access));
    }
}
