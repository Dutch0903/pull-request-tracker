package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.presentation.cli.dialog.form.Field;

import java.util.Optional;

public interface FilterDefinition {
    String ALL = "All";

    String fieldId();

    String label();

    Field createFormField(String currentValue);

    Optional<PullRequestFilter> toDomainFilter(String value);

    String formatDisplayValue(String value);

    default boolean isActive(String value) {
        return value != null && !value.isBlank() && !ALL.equals(value);
    }

    default String defaultValue() {
        return null;
    }
}
