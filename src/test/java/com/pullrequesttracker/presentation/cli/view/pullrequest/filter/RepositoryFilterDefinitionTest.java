package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.application.usecase.FetchAllCodeRepositories;
import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.filter.PullRequestFilterField;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RepositoryFilterDefinitionTest {
    @Mock
    private FetchAllCodeRepositories fetchAllCodeRepositories;

    @InjectMocks
    private RepositoryFilterDefinition definition;

    @Test
    void toDomainFilter_withMatchingRepository_shouldReturnCodeRepositoryFilter() {
        UUID repoId = UUID.randomUUID();
        when(fetchAllCodeRepositories.execute()).thenReturn(List.of(new CodeRepositoryDto(repoId, "myparcel", "sdk")));

        Optional<PullRequestFilter> result = definition.toDomainFilter("myparcel/sdk");

        assertThat(result).isPresent();
        assertThat(result.get().field()).isEqualTo(PullRequestFilterField.CODE_REPOSITORY);
        assertThat(result.get().value()).isEqualTo(CodeRepositoryId.from(repoId));
    }

    @Test
    void toDomainFilter_withAll_shouldReturnEmpty() {
        Optional<PullRequestFilter> result = definition.toDomainFilter(FilterDefinition.ALL);

        assertThat(result).isEmpty();
    }

    @Test
    void toDomainFilter_withNull_shouldReturnEmpty() {
        Optional<PullRequestFilter> result = definition.toDomainFilter(null);

        assertThat(result).isEmpty();
    }

    @Test
    void formatDisplayValue_withOwnerAndName_shouldReturnName() {
        String result = definition.formatDisplayValue("myparcel/sdk");

        assertThat(result).isEqualTo("sdk");
    }
}
