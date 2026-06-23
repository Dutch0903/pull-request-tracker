package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.CodeRepositorySummaryDto;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FetchCodeRepositorySummariesTest {
    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @Mock
    private PullRequestRepository pullRequestRepository;

    @InjectMocks
    private FetchCodeRepositorySummaries fetchCodeRepositorySummaries;

    @Test
    void execute_whenNoCodeRepositoriesExists_shouldReturnEmptyList() {
        when(codeRepositoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<CodeRepositorySummaryDto> result = fetchCodeRepositorySummaries.execute();

        assertTrue(result.isEmpty());
    }

    @Test
    void execute_whenCalled_shouldReturnASummaryForEachCodeRepository() {
        CodeRepository codeRepository1 = aCodeRepository().build();
        CodeRepository codeRepository2 = aCodeRepository().build();

        when(codeRepositoryRepository.findAll()).thenReturn(List.of(codeRepository1, codeRepository2));
        when(pullRequestRepository.countAllByCodeRepositoryId())
                .thenReturn(Map.of(codeRepository1.getId(), 2, codeRepository2.getId(), 1));

        List<CodeRepositorySummaryDto> result = fetchCodeRepositorySummaries.execute();

        assertEquals(2, result.size());

        assertThat(result).containsExactly(new CodeRepositorySummaryDto(codeRepository1.getFullName().toString(), 2),
                new CodeRepositorySummaryDto(codeRepository2.getFullName().toString(), 1));
    }
}
