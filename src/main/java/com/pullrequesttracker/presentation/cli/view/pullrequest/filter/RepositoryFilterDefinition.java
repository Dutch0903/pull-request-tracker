package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.application.usecase.FetchAllCodeRepositories;
import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
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
@Order(3)
@RequiredArgsConstructor
public class RepositoryFilterDefinition implements FilterDefinition {
    private final FetchAllCodeRepositories fetchAllCodeRepositories;

    @Override
    public String fieldId() {
        return FilterFormFields.REPOSITORY;
    }

    @Override
    public String label() {
        return "Repository";
    }

    @Override
    public Field createFormField(String currentValue) {
        List<String> options = new ArrayList<>();
        options.add(ALL);
        fetchAllCodeRepositories.execute().forEach(r -> options.add(r.owner() + "/" + r.name()));
        return new SelectField(fieldId(), label(), options, currentValue != null ? currentValue : ALL);
    }

    @Override
    public Optional<PullRequestFilter> toDomainFilter(String value) {
        if (!isActive(value)) {
            return Optional.empty();
        }
        return fetchAllCodeRepositories.execute().stream().filter(r -> (r.owner() + "/" + r.name()).equals(value))
                .map(r -> CodeRepositoryId.from(r.id())).map(PullRequestFilter::codeRepository).findFirst();
    }

    @Override
    public String formatDisplayValue(String value) {
        int slash = value.lastIndexOf('/');
        return slash >= 0 ? value.substring(slash + 1) : value;
    }
}
