package com.prtracker.coderepository.domain.service;

import com.prtracker.coderepository.domain.exception.CodeRepositoryAlreadyExistsException;
import com.prtracker.coderepository.domain.exception.TokenNotFoundException;
import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.FullName;
import com.prtracker.coderepository.domain.port.CodeRepositoryRepository;
import com.prtracker.coderepository.domain.port.TokenExistencePort;
import com.prtracker.shared.kernel.TokenId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.prtracker.testfixtures.coderepository.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CodeRepositoryServiceTest {

    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @Mock
    private TokenExistencePort tokenExistencePort;

    @InjectMocks
    private CodeRepositoryDomainService codeRepositoryDomainService;

    @Test
    void add_whenFullNameDoesNotExists_shouldSaveCodeRepository() {
        CodeRepository codeRepository = aCodeRepository().withFullName(new FullName("unique", "repo")).build();

        when(codeRepositoryRepository.exists(codeRepository.getFullName())).thenReturn(false);
        when(tokenExistencePort.exists(codeRepository.getTokenId())).thenReturn(true);

        codeRepositoryDomainService.add(codeRepository);

        verify(codeRepositoryRepository, times(1)).exists(codeRepository.getFullName());
        verify(tokenExistencePort, times(1)).exists(codeRepository.getTokenId());
        verify(codeRepositoryRepository, times(1)).save(codeRepository);
    }

    @Test
    void add_whenFullNameAlreadyExists_shouldThrowCodeRepositoryAlreadyExistsException() {
        CodeRepository codeRepository = aCodeRepository().withFullName(new FullName("already", "exists")).build();

        when(codeRepositoryRepository.exists(codeRepository.getFullName())).thenReturn(true);

        CodeRepositoryAlreadyExistsException exception = assertThrows(CodeRepositoryAlreadyExistsException.class,
                () -> codeRepositoryDomainService.add(codeRepository));

        verify(codeRepositoryRepository, never()).save(codeRepository);

        assertEquals("Code Repository " + codeRepository.getFullName() + " already exists", exception.getMessage());
    }

    @Test
    void add_whenTokenDoesNotExists_shouldThrowTokenNotFoundException() {
        CodeRepository codeRepository = aCodeRepository().withTokenId(TokenId.create()).build();

        when(codeRepositoryRepository.exists(codeRepository.getFullName())).thenReturn(false);
        when(tokenExistencePort.exists(codeRepository.getTokenId())).thenReturn(false);

        TokenNotFoundException exception = assertThrows(TokenNotFoundException.class,
                () -> codeRepositoryDomainService.add(codeRepository));

        verify(codeRepositoryRepository, never()).save(codeRepository);

        assertEquals("Token not found: " + codeRepository.getTokenId().value(), exception.getMessage());
    }

    @Test
    void add_whenCodeRepositoryDoesNotHaveAToken_shouldNotCheckForExistingToken() {
        CodeRepository codeRepository = aCodeRepository().withTokenId(null).build();

        when(codeRepositoryRepository.exists(codeRepository.getFullName())).thenReturn(false);

        codeRepositoryDomainService.add(codeRepository);

        verify(tokenExistencePort, never()).exists(any());
        verify(codeRepositoryRepository, times(1)).save(codeRepository);
    }
}
