package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.valueobject.FullName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.pullrequesttracker.testfixtures.domain.model.CodeRepositoryTestBuilder.aCodeRepository;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FetchRecentCodeRepositoriesTest {
    @Mock
    private CodeRepositoryRepository codeRepositoryRepository;

    @InjectMocks
    private FetchRecentCodeRepositories fetchRecentCodeRepositories;

    @Test
    public void execute_whenCalled_shouldReturnCodeRepositoryDtos() {
        CodeRepository codeRepository1 = aCodeRepository().withFullName(new FullName("owner1", "name1")).build();
        CodeRepository codeRepository2 = aCodeRepository().withFullName(new FullName("owner2", "name2")).build();

        when(codeRepositoryRepository.findAll()).thenReturn(List.of(codeRepository1, codeRepository2));

        List<CodeRepositoryDto> result = fetchRecentCodeRepositories.execute();

        assertThat(result).containsExactly(
                new CodeRepositoryDto(codeRepository1.getId().value(), codeRepository1.getFullName().owner(), codeRepository1.getFullName().name()),
                new CodeRepositoryDto(codeRepository2.getId().value(), codeRepository2.getFullName().owner(), codeRepository2.getFullName().name())
        );
    }
}
