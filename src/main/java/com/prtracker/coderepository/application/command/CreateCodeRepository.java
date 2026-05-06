package com.prtracker.coderepository.application.command;

import com.prtracker.coderepository.application.parser.CodeRepositoryReferenceParser;
import com.prtracker.coderepository.application.parser.ParsedCodeRepositoryReference;
import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.FullName;
import com.prtracker.coderepository.domain.service.CodeRepositoryDomainService;
import com.prtracker.shared.kernel.CodeRepositoryId;
import com.prtracker.shared.kernel.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCodeRepository {
    private final CodeRepositoryReferenceParser parser;
    private final CodeRepositoryDomainService codeRepositoryDomainService;

    public void execute(CreateCodeRepositoryDto dto) {
        ParsedCodeRepositoryReference parsedReference = parser.parse(dto.repositoryReference());

        CodeRepository codeRepository = new CodeRepository(
                CodeRepositoryId.create(),
                new FullName(parsedReference.owner(), parsedReference.name()),
                dto.tokenId() != null ? TokenId.from(dto.tokenId()) : null
        );

        codeRepositoryDomainService.add(codeRepository);
    }
}
