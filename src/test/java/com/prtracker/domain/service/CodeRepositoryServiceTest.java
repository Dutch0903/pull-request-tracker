package com.prtracker.domain.service;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.exceptions.CodeRepositoryAlreadyExistsException;
import com.prtracker.domain.exceptions.TokenNotFoundException;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import com.prtracker.domain.repository.TokenRepository;
import com.prtracker.domain.valueobject.FullName;
import com.prtracker.domain.valueobject.TokenId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.prtracker.testfixtures.domain.entity.CodeRepositoryTestBuilder.aCodeRepository;
import static com.prtracker.testfixtures.domain.entity.TokenTestBuilder.aToken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CodeRepositoryServiceTest {

    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private CodeRepositoryService codeRepositoryService;

    @Test
    void add_whenFullNameDoesNotExists_shouldSaveCodeRepository() {
        CodeRepository codeRepository = aCodeRepository().withFullName(new FullName("unique", "repo")).build();

        when(codeRepositoryRepository.exists(codeRepository.getFullName())).thenReturn(false);

        when(tokenRepository.findById(codeRepository.getTokenId()))
                .thenReturn(Optional.of(aToken().withTokenId(codeRepository.getTokenId()).build()));

        codeRepositoryService.add(codeRepository);

        verify(codeRepositoryRepository, times(1)).exists(codeRepository.getFullName());
        verify(tokenRepository, times(1)).findById(codeRepository.getTokenId());
        verify(codeRepositoryRepository, times(1)).save(codeRepository);
    }

    @Test
    void add_whenFullNameAlreadyExists_shouldThrowCodeRepositoryAlreadyExistsException() {
        CodeRepository codeRepository = aCodeRepository().withFullName(new FullName("already", "exists")).build();

        when(codeRepositoryRepository.exists(codeRepository.getFullName())).thenReturn(true);

        CodeRepositoryAlreadyExistsException exception = assertThrows(CodeRepositoryAlreadyExistsException.class,
                () -> codeRepositoryService.add(codeRepository));

        verify(codeRepositoryRepository, never()).save(codeRepository);

        assertEquals("Code Repository " + codeRepository.getFullName() + " already exists", exception.getMessage());
    }

    @Test
    void add_whenTokenDoesNotExists_shouldThrowTokenNotFoundException() {
        CodeRepository codeRepository = aCodeRepository().withTokenId(TokenId.create()).build();

        when(codeRepositoryRepository.exists(codeRepository.getFullName())).thenReturn(false);

        when(tokenRepository.findById(codeRepository.getTokenId())).thenReturn(Optional.empty());

        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,
                () -> codeRepositoryService.add(codeRepository));

        verify(codeRepositoryRepository, never()).save(codeRepository);

        assertEquals("Token not found: " + codeRepository.getTokenId().value(), exception.getMessage());
    }

    @Test
    void add_whenCodeRepositoryDoesNotHaveAToken_shouldNotCheckForExistingToken() {
        CodeRepository codeRepository = aCodeRepository().withTokenId(null).build();

        when(codeRepositoryRepository.exists(codeRepository.getFullName())).thenReturn(false);

        codeRepositoryService.add(codeRepository);

        verify(tokenRepository, never()).findById(codeRepository.getTokenId());

        verify(codeRepositoryRepository, times(1)).save(codeRepository);

    }
}
