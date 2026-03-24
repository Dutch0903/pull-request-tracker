package com.prtracker.domain.service;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.exceptions.CodeRepositoryAlreadyExistsException;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.prtracker.testfixtures.CodeRepositoryTestBuilder.aCodeRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CodeRepositoryServiceTest {

    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @InjectMocks
    private CodeRepositoryService codeRepositoryService;

    @Test
    void add_whenIdentifierDoesNotExists_shouldSaveCodeRepository() {
        CodeRepository codeRepository = aCodeRepository().withIdentifier(CodeRepositoryIdentifier.from("unique/repo"))
                .build();

        when(codeRepositoryRepository.exists(codeRepository.getIdentifier())).thenReturn(false);

        codeRepositoryService.add(codeRepository);

        verify(codeRepositoryRepository, times(1)).exists(codeRepository.getIdentifier());
        verify(codeRepositoryRepository, times(1)).save(codeRepository);
    }

    @Test
    void add_whenIdentifierAlreadyExists_shouldThrowCodeRepositoryAlreadyExistsException() {
        CodeRepository codeRepository = aCodeRepository()
                .withIdentifier(CodeRepositoryIdentifier.from("already/exists")).build();

        when(codeRepositoryRepository.exists(codeRepository.getIdentifier())).thenReturn(true);

        CodeRepositoryAlreadyExistsException exception = assertThrows(CodeRepositoryAlreadyExistsException.class,
                () -> codeRepositoryService.add(codeRepository));

        verify(codeRepositoryRepository, times(0)).save(codeRepository);

        assertEquals("Code Repository with identifier " + codeRepository.getIdentifier().value() + " already exists",
                exception.getMessage());
    }
}
