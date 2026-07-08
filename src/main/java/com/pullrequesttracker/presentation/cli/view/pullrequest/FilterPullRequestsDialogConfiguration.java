package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.presentation.cli.dialog.form.Field;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogConfiguration;
import com.pullrequesttracker.presentation.cli.view.pullrequest.filter.FilterDefinition;

import java.util.List;

public class FilterPullRequestsDialogConfiguration implements FormDialogConfiguration {
    private final List<FilterDefinition> definitions;
    private final PullRequestListState state;

    public FilterPullRequestsDialogConfiguration(List<FilterDefinition> definitions, PullRequestListState state) {
        this.definitions = definitions;
        this.state = state;
    }

    @Override
    public List<Field> fields() {
        return definitions.stream().map(def -> def.createFormField(state.getFilterValue(def.fieldId()))).toList();
    }

    @Override
    public String title() {
        return "Filter Pull Requests";
    }

    @Override
    public String description() {
        return "Filter the pull request list.";
    }
}
