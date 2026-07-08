package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.filter.PullRequestFilterField;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class SearchFilterDefinitionTest {
    private final SearchFilterDefinition definition = new SearchFilterDefinition();

    @Test
    void toDomainFilter_withText_shouldReturnSearchFilter() {
        Optional<PullRequestFilter> result = definition.toDomainFilter("fix login");

        assertThat(result).isPresent();
        assertThat(result.get().field()).isEqualTo(PullRequestFilterField.SEARCH);
        assertThat(result.get().value()).isEqualTo("fix login");
    }

    @Test
    void toDomainFilter_withBlank_shouldReturnEmpty() {
        Optional<PullRequestFilter> result = definition.toDomainFilter("   ");

        assertThat(result).isEmpty();
    }

    @Test
    void toDomainFilter_withNull_shouldReturnEmpty() {
        Optional<PullRequestFilter> result = definition.toDomainFilter(null);

        assertThat(result).isEmpty();
    }

    @Test
    void isActive_withBlankValue_shouldReturnFalse() {
        assertThat(definition.isActive("  ")).isFalse();
    }

    @Test
    void isActive_withText_shouldReturnTrue() {
        assertThat(definition.isActive("fix")).isTrue();
    }
}
