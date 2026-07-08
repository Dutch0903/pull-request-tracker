package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.presentation.cli.dialog.form.Field;
import com.pullrequesttracker.presentation.cli.dialog.form.TextField;
import com.pullrequesttracker.presentation.cli.view.pullrequest.FilterFormFields;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Order(2)
public class SearchFilterDefinition implements FilterDefinition {
    @Override
    public String fieldId() {
        return FilterFormFields.SEARCH;
    }

    @Override
    public String label() {
        return "Search";
    }

    @Override
    public Field createFormField(String currentValue) {
        return new TextField(fieldId(), label(), false, currentValue != null ? currentValue : "");
    }

    @Override
    public Optional<PullRequestFilter> toDomainFilter(String value) {
        if (!isActive(value)) {
            return Optional.empty();
        }
        return Optional.of(PullRequestFilter.search(value));
    }

    @Override
    public String formatDisplayValue(String value) {
        return "\"" + value + "\"";
    }

    @Override
    public boolean isActive(String value) {
        return value != null && !value.isBlank();
    }
}
