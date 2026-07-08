package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.application.usecase.FetchAllAuthors;
import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.presentation.cli.dialog.form.Field;
import com.pullrequesttracker.presentation.cli.dialog.form.SelectField;
import com.pullrequesttracker.presentation.cli.view.pullrequest.FilterFormFields;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Order(4)
@RequiredArgsConstructor
public class AuthorFilterDefinition implements FilterDefinition {
    private final FetchAllAuthors fetchAllAuthors;

    @Override
    public String fieldId() {
        return FilterFormFields.AUTHOR;
    }

    @Override
    public String label() {
        return "Author";
    }

    @Override
    public Field createFormField(String currentValue) {
        List<String> options = new ArrayList<>();
        options.add(ALL);
        options.addAll(fetchAllAuthors.execute());
        return new SelectField(fieldId(), label(), options, currentValue != null ? currentValue : ALL);
    }

    @Override
    public Optional<PullRequestFilter> toDomainFilter(String value) {
        if (!isActive(value)) {
            return Optional.empty();
        }
        return Optional.of(PullRequestFilter.author(value));
    }

    @Override
    public String formatDisplayValue(String value) {
        return value;
    }
}
