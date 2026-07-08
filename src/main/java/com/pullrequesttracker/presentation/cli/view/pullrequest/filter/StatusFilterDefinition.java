package com.pullrequesttracker.presentation.cli.view.pullrequest.filter;

import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.presentation.cli.dialog.form.Field;
import com.pullrequesttracker.presentation.cli.dialog.form.SelectField;
import com.pullrequesttracker.presentation.cli.view.pullrequest.FilterFormFields;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
@Order(1)
public class StatusFilterDefinition implements FilterDefinition {
    private static final Map<String, PullRequestStatus> STATUS_OPTIONS = Map.of("Open", PullRequestStatus.OPEN,
            "Merged", PullRequestStatus.MERGED, "Closed", PullRequestStatus.CLOSED, "Ignored",
            PullRequestStatus.IGNORED);

    @Override
    public String fieldId() {
        return FilterFormFields.STATUS;
    }

    @Override
    public String label() {
        return "Status";
    }

    @Override
    public Field createFormField(String currentValue) {
        List<String> options = new ArrayList<>();
        options.add(ALL);
        options.addAll(STATUS_OPTIONS.keySet().stream().sorted().toList());
        return new SelectField(fieldId(), label(), options, currentValue != null ? currentValue : ALL);
    }

    @Override
    public Optional<PullRequestFilter> toDomainFilter(String value) {
        if (!isActive(value)) {
            return Optional.empty();
        }
        PullRequestStatus status = STATUS_OPTIONS.get(value);
        return status != null ? Optional.of(PullRequestFilter.status(status)) : Optional.empty();
    }

    @Override
    public String formatDisplayValue(String value) {
        return value;
    }

    @Override
    public String defaultValue() {
        return "Open";
    }
}
