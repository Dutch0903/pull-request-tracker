package com.prtracker.coderepository.application.command;

import com.prtracker.coderepository.application.parser.CodeRepositoryReferenceParser;
import com.prtracker.coderepository.application.parser.ParsedCodeRepositoryReference;
import com.prtracker.coderepository.domain.model.CodeRepositoryReferenceType;
import com.prtracker.coderepository.domain.service.CodeRepositoryDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.prtracker.testfixtures.coderepository.application.command.CreateCodeRepositoryDtoTestBuilder.aCreateCodeRepositoryDto;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCodeRepositoryCommandTest {
    @Mock
    private CodeRepositoryReferenceParser codeRepositoryReferenceParser;

    @Mock
    private CodeRepositoryDomainService codeRepositoryDomainService;

    @InjectMocks
    private CreateCodeRepository createCodeRepository;

    @Test
    void execute_whenCalled_shouldParseCodeRepositoryReferenceAndSaveCodeRepository() {
        String owner = "owner";
        String name = "name";
        CreateCodeRepositoryDto dto = aCreateCodeRepositoryDto().withRepositoryReference(owner + "/" + name).build();

        when(codeRepositoryReferenceParser.parse(dto.repositoryReference()))
                .thenReturn(new ParsedCodeRepositoryReference(owner, name, CodeRepositoryReferenceType.OWNER_NAME));

        createCodeRepository.execute(dto);

        verify(codeRepositoryReferenceParser, times(1)).parse(dto.repositoryReference());

        verify(codeRepositoryDomainService, times(1)).add(argThat(
                argument -> argument.getFullName().owner().equals(owner) && argument.getFullName().name().equals(name)
                        && argument.getTokenId().value().equals(dto.tokenId())));
    }
}
