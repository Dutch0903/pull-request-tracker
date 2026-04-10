package com.prtracker.application.command;

import com.prtracker.application.command.dto.CreateCodeRepositoryDto;
import com.prtracker.application.parser.CodeRepositoryReferenceParser;
import com.prtracker.application.parser.ParsedCodeRepositoryReference;
import com.prtracker.domain.enums.CodeRepositoryReferenceType;
import com.prtracker.domain.service.CodeRepositoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.prtracker.testfixtures.application.command.dto.CreateCodeRepositoryDtoTestBuilder.aCreateCodeRepositoryDto;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCodeRepositoryCommandTest {
    @Mock
    private CodeRepositoryReferenceParser codeRepositoryReferenceParser;

    @Mock
    private CodeRepositoryService codeRepositoryService;

    @InjectMocks
    private CreateCodeRepositoryCommand createCodeRepositoryCommand;

    @Test
    void execute_whenCalled_shouldParseCodeRepositoryReferenceAndSaveCodeRepository()
    {
        String owner = "owner";
        String name = "name";
        CreateCodeRepositoryDto dto = aCreateCodeRepositoryDto()
                .withRepositoryReference(owner + "/" + name)
                .build();

        when(codeRepositoryReferenceParser.parse(dto.repositoryReference()))
                .thenReturn(new ParsedCodeRepositoryReference(owner, name, CodeRepositoryReferenceType.OWNER_NAME));

        createCodeRepositoryCommand.execute(dto);

        verify(codeRepositoryReferenceParser, times(1)).parse(dto.repositoryReference());

        verify(codeRepositoryService, times(1)).add(
                argThat(argument -> argument.getFullName().owner().equals(owner)
                        && argument.getFullName().name().equals(name)
                        && argument.getTokenId().value().equals(dto.tokenId()))
        );
    }
}
