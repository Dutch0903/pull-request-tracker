package com.prtracker.application.command;

import com.prtracker.application.command.dto.CreateCodeRepositoryDto;
import com.prtracker.application.parser.CodeRepositoryReferenceParser;
import com.prtracker.application.parser.ParsedCodeRepositoryReference;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.service.CodeRepositoryService;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import com.prtracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateCodeRepositoryCommand extends Command<CreateCodeRepositoryDto, Void> {
    private final CodeRepositoryReferenceParser parser;
    private final CodeRepositoryService codeRepositoryService;

    @Override
    protected Void executeInternal(CreateCodeRepositoryDto input) {
        ParsedCodeRepositoryReference parsedCodeRepositoryReference = parser.parse(input.repositoryReference());
        CodeRepositoryIdentifier identifier = parsedCodeRepositoryReference.getIdentifier();

        CodeRepository codeRepository = new CodeRepository(identifier, parsedCodeRepositoryReference.owner(),
                parsedCodeRepositoryReference.name(), TokenId.from(input.tokenId()));

        codeRepositoryService.add(codeRepository);

        return null;
    }
}
