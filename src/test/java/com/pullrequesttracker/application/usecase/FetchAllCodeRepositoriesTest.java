package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FetchAllCodeRepositoriesTest {
    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @InjectMocks
    private FetchAllCodeRepositories fetchAllCodeRepositories;

    @Test
    void execute_whenCalled_shouldMapCodeRepositoryToDto() {
        CodeRepository codeRepository1 = aCodeRepository().build();
        CodeRepository codeRepository2 = aCodeRepository().build();

        when(codeRepositoryRepository.findAll()).thenReturn(List.of(codeRepository1, codeRepository2));

        List<CodeRepositoryDto> result = fetchAllCodeRepositories.execute();

        assertThat(result).containsExactly(
                new CodeRepositoryDto(codeRepository1.getId().value(), codeRepository1.getFullName().owner(),
                        codeRepository1.getFullName().name()),
                new CodeRepositoryDto(codeRepository2.getId().value(), codeRepository2.getFullName().owner(),
                        codeRepository2.getFullName().name()));
    }

    @Test
    void execute_whenNoCodeRepositoryFound_shouldReturnEmptyList() {
        List<CodeRepositoryDto> result = fetchAllCodeRepositories.execute();

        assertThat(result).isEmpty();
    }
}
