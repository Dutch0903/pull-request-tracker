package com.pullrequesttracker.domain.service;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodeRepositoryDomainServiceTest {
    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private CodeRepositoryDomainService codeRepositoryDomainService;

    @Test
    void add_whenFullNameDoesNotExist_shouldSaveCodeRepository() {
        CodeRepository repo = aCodeRepository().build();

        when(codeRepositoryRepository.exists(repo.getFullName())).thenReturn(false);
        when(tokenRepository.findById(repo.getTokenId())).thenReturn(Optional.of(mock(Token.class)));

        codeRepositoryDomainService.add(repo);

        verify(codeRepositoryRepository).save(repo);
    }

    @Test
    void add_whenFullNameAlreadyExists_shouldThrowIllegalStateException() {
        CodeRepository repo = aCodeRepository().withFullName(new FullName("already", "exists")).build();

        when(codeRepositoryRepository.exists(repo.getFullName())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> codeRepositoryDomainService.add(repo));

        verify(codeRepositoryRepository, never()).save(any());
    }

    @Test
    void add_whenTokenNotFound_shouldThrowIllegalStateException() {
        CodeRepository repo = aCodeRepository().withTokenId(TokenId.create()).build();

        when(codeRepositoryRepository.exists(repo.getFullName())).thenReturn(false);
        when(tokenRepository.findById(repo.getTokenId())).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> codeRepositoryDomainService.add(repo));

        verify(codeRepositoryRepository, never()).save(any());
    }

    @Test
    void add_whenNoToken_shouldNotCheckTokenRepository() {
        CodeRepository repo = aCodeRepository().withTokenId(null).build();

        when(codeRepositoryRepository.exists(repo.getFullName())).thenReturn(false);

        codeRepositoryDomainService.add(repo);

        verify(tokenRepository, never()).findById(any());
        verify(codeRepositoryRepository).save(repo);
    }

    @Test
    void add_whenFullNameAlreadyExists_shouldContainRepoNameInMessage() {
        CodeRepository repo = aCodeRepository().withFullName(new FullName("owner", "repo")).build();

        when(codeRepositoryRepository.exists(repo.getFullName())).thenReturn(true);

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> codeRepositoryDomainService.add(repo));

        assertTrue(ex.getMessage().contains("owner/repo"));
    }
}
