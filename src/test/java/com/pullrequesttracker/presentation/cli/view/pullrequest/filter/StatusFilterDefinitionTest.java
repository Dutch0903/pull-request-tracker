package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.filter.PullRequestFilterField;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class StatusFilterDefinitionTest {
    private final StatusFilterDefinition definition = new StatusFilterDefinition();

    @Test
    void toDomainFilter_withOpenStatus_shouldReturnStatusFilter() {
        Optional<PullRequestFilter> result = definition.toDomainFilter("Open");

        assertThat(result).isPresent();
        assertThat(result.get().field()).isEqualTo(PullRequestFilterField.STATUS);
        assertThat(result.get().value()).isEqualTo(PullRequestStatus.OPEN);
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
    void isActive_withNull_shouldReturnFalse() {
        assertThat(definition.isActive(null)).isFalse();
    }

    @Test
    void isActive_withAll_shouldReturnFalse() {
        assertThat(definition.isActive(FilterDefinition.ALL)).isFalse();
    }

    @Test
    void isActive_withValue_shouldReturnTrue() {
        assertThat(definition.isActive("Open")).isTrue();
    }

    @Test
    void defaultValue_shouldReturnOpen() {
        assertThat(definition.defaultValue()).isEqualTo("Open");
    }
}
