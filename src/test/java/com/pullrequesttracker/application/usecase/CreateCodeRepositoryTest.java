package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.parser.CodeRepositoryReferenceParser;
import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.model.RepositoryAccess;
import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.type.CodeRepositoryReferenceType;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateCodeRepositoryTest {
    @Mock
    private CodeRepositoryReferenceParser parser;

    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private CreateCodeRepository createCodeRepository;

    private static final String OWNER = "owner";
    private static final String NAME = "name";
    private static final Platform PLATFORM = Platform.GITHUB;

    @BeforeEach
    void setUp() {
        when(parser.parse(OWNER + "/" + NAME, PLATFORM))
                .thenReturn(new ParsedCodeRepositoryReference(OWNER, NAME, CodeRepositoryReferenceType.OWNER_NAME));
    }

    @Test
    void execute_whenCalled_shouldParseReferenceAndSave() {
        TokenId tokenId = TokenId.create();
        when(tokenRepository.findById(tokenId)).thenReturn(Optional.of(mock(Token.class)));

        createCodeRepository.execute(OWNER + "/" + NAME, PLATFORM, new RepositoryAccess.Authenticated(tokenId));

        verify(codeRepositoryRepository)
                .save(argThat(repo -> repo.getFullName().owner().equals(OWNER) && repo.getFullName().name().equals(NAME)
                        && repo.getPlatform() == PLATFORM && repo.getTokenId().equals(Optional.of(tokenId))));
    }

    @Test
    void execute_whenTokenIdIsNull_shouldSaveWithoutToken() {
        createCodeRepository.execute(OWNER + "/" + NAME, PLATFORM, new RepositoryAccess.Public());

        verify(codeRepositoryRepository).save(argThat(repo -> repo.getTokenId().isEmpty()));
        verify(tokenRepository, never()).findById(any());
    }

    @Test
    void execute_whenFullNameAlreadyExists_shouldThrowIllegalStateException() {
        when(codeRepositoryRepository.exists(any())).thenReturn(true);

        assertThrows(IllegalStateException.class,
                () -> createCodeRepository.execute(OWNER + "/" + NAME, PLATFORM, new RepositoryAccess.Public()));

        verify(codeRepositoryRepository, never()).save(any());
    }

    @Test
    void execute_whenFullNameAlreadyExists_shouldContainRepoNameInMessage() {
        when(codeRepositoryRepository.exists(any())).thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> createCodeRepository.execute(OWNER + "/" + NAME, PLATFORM, new RepositoryAccess.Public()));

        assertTrue(ex.getMessage().contains(OWNER + "/" + NAME));
    }

    @Test
    void execute_whenTokenNotFound_shouldThrowIllegalStateException() {
        TokenId tokenId = TokenId.create();
        when(tokenRepository.findById(tokenId)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> createCodeRepository.execute(OWNER + "/" + NAME, PLATFORM,
                new RepositoryAccess.Authenticated(tokenId)));

        verify(codeRepositoryRepository, never()).save(any());
    }

    @Test
    void execute_whenNoToken_shouldNotCheckTokenRepository() {
        createCodeRepository.execute(OWNER + "/" + NAME, PLATFORM, new RepositoryAccess.Public());

        verify(tokenRepository, never()).findById(any());
        verify(codeRepositoryRepository).save(any());
    }
}
