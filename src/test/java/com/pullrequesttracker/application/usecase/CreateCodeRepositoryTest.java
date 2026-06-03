package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.parser.CodeRepositoryReferenceParser;
import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.service.CodeRepositoryDomainService;
import com.pullrequesttracker.domain.type.CodeRepositoryReferenceType;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCodeRepositoryTest {
    @Mock
    private CodeRepositoryReferenceParser parser;

    @Mock
    private CodeRepositoryDomainService codeRepositoryDomainService;

    @InjectMocks
    private CreateCodeRepository createCodeRepository;

    @Test
    void execute_whenCalled_shouldParseReferenceAndSave() {
        String owner = "owner";
        String name = "name";
        TokenId tokenId = TokenId.create();

        when(parser.parse(owner + "/" + name, Platform.GITHUB))
                .thenReturn(new ParsedCodeRepositoryReference(owner, name, CodeRepositoryReferenceType.OWNER_NAME));

        createCodeRepository.execute(owner + "/" + name, Platform.GITHUB, tokenId);

        verify(parser).parse(owner + "/" + name, Platform.GITHUB);
        verify(codeRepositoryDomainService).add(argThat(repo ->
                repo.getFullName().owner().equals(owner)
                        && repo.getFullName().name().equals(name)
                        && repo.getPlatform() == Platform.GITHUB
                        && repo.getTokenId().equals(tokenId)));
    }

    @Test
    void execute_whenTokenIdIsNull_shouldSaveWithoutToken() {
        when(parser.parse("owner/name", Platform.GITHUB))
                .thenReturn(new ParsedCodeRepositoryReference("owner", "name", CodeRepositoryReferenceType.OWNER_NAME));

        createCodeRepository.execute("owner/name", Platform.GITHUB, null);

        verify(codeRepositoryDomainService).add(argThat(repo -> repo.getTokenId() == null));
    }
}
