package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.parser.CodeRepositoryReferenceParser;
import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.service.CodeRepositoryDomainService;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCodeRepository {
    private final CodeRepositoryReferenceParser parser;
    private final CodeRepositoryDomainService codeRepositoryDomainService;

    public void execute(String repositoryReference, Platform platform, @Nullable TokenId tokenId) {
        ParsedCodeRepositoryReference parsed = parser.parse(repositoryReference, platform);

        CodeRepository codeRepository = new CodeRepository(
                CodeRepositoryId.create(),
                new FullName(parsed.owner(), parsed.name()),
                platform,
                tokenId);

        codeRepositoryDomainService.add(codeRepository);
    }
}
