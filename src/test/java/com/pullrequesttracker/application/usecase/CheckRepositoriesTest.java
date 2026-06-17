package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CheckRepositoriesTest {

    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @Mock
    private SynchronizeCodeRepository synchronizeCodeRepository;

    @InjectMocks
    private CheckRepositories checkRepositories;

    @Test
    void execute_whenNoRepositoriesExist_shouldNotSynchronizeAnyRepository() {
        when(codeRepositoryRepository.findAll()).thenReturn(List.of());

        checkRepositories.execute();

        verifyNoInteractions(synchronizeCodeRepository);
    }

    @Test
    void execute_whenMultipleRepositoriesExist_shouldSynchronizeEachRepository() {
        CodeRepository repoA = aCodeRepository().build();
        CodeRepository repoB = aCodeRepository().build();

        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repoA, repoB));
        when(synchronizeCodeRepository.execute(repoA)).thenReturn(CompletableFuture.completedFuture(null));
        when(synchronizeCodeRepository.execute(repoB)).thenReturn(CompletableFuture.completedFuture(null));

        checkRepositories.execute();

        verify(synchronizeCodeRepository).execute(repoA);
        verify(synchronizeCodeRepository).execute(repoB);
    }

    @Test
    void execute_whenOneRepositorySynchronizationFails_shouldStillSynchronizeOtherRepositories() {
        CodeRepository repoA = aCodeRepository().build();
        CodeRepository repoB = aCodeRepository().build();

        when(codeRepositoryRepository.findAll()).thenReturn(List.of(repoA, repoB));
        when(synchronizeCodeRepository.execute(repoA)).thenReturn(CompletableFuture.failedFuture(new RuntimeException("sync failed")));
        when(synchronizeCodeRepository.execute(repoB)).thenReturn(CompletableFuture.completedFuture(null));

        checkRepositories.execute();

        verify(synchronizeCodeRepository).execute(repoA);
        verify(synchronizeCodeRepository).execute(repoB);
    }
}
