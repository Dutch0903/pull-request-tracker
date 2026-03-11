package com.prtracker.application.usecase;

import com.prtracker.application.dto.AddCodeRepositoryDto;
import com.prtracker.application.parser.CodeRepositoryIdentifierParser;
import com.prtracker.application.parser.ParsedCodeRepository;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.service.CodeRepositoryService;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AddCodeRepositoryUseCase extends AbstractUseCase<AddCodeRepositoryDto, Void> {
    private final CodeRepositoryIdentifierParser parser;
    private final CodeRepositoryService codeRepositoryService;

    @Override
    protected Void executeInternal(AddCodeRepositoryDto input) {
        ParsedCodeRepository parsedCodeRepository = parser.parse(input.name());
        CodeRepositoryIdentifier identifier = parsedCodeRepository.getIdentifier();

        CodeRepository codeRepository = new CodeRepository(
                identifier,
                parsedCodeRepository.owner(),
                parsedCodeRepository.name(),
                input.name(),
                input.accessToken()
        );

        codeRepositoryService.add(codeRepository);

        log.info("Added repository {}", identifier.value());

        return null;
    }
}
