package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.application.usecase.FetchAllAuthors;
import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.filter.PullRequestFilterField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthorFilterDefinitionTest {
    @Mock
    private FetchAllAuthors fetchAllAuthors;

    @InjectMocks
    private AuthorFilterDefinition definition;

    @Test
    void toDomainFilter_withAuthor_shouldReturnAuthorFilter() {
        Optional<PullRequestFilter> result = definition.toDomainFilter("alice");

        assertThat(result).isPresent();
        assertThat(result.get().field()).isEqualTo(PullRequestFilterField.AUTHOR);
        assertThat(result.get().value()).isEqualTo("alice");
    }

    @Test
    void toDomainFilter_withNull_shouldReturnEmpty() {
        Optional<PullRequestFilter> result = definition.toDomainFilter(null);

        assertThat(result).isEmpty();
    }

    @Test
    void toDomainFilter_withAll_shouldReturnEmpty() {
        Optional<PullRequestFilter> result = definition.toDomainFilter(FilterDefinition.ALL);

        assertThat(result).isEmpty();
    }
}
