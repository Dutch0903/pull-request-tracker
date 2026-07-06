package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.presentation.cli.dialog.form.Field;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogConfiguration;
import com.pullrequesttracker.presentation.cli.dialog.form.SelectField;
import com.pullrequesttracker.presentation.cli.dialog.form.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FilterPullRequestsDialogConfiguration implements FormDialogConfiguration {
    public static final String ALL = "All";

    public static final Map<String, PullRequestStatus> STATUS_OPTIONS = Map.of("Open", PullRequestStatus.OPEN, "Merged",
            PullRequestStatus.MERGED, "Closed", PullRequestStatus.CLOSED, "Ignored", PullRequestStatus.IGNORED);

    private final List<String> repositoryOptions;
    private final String initialStatus;
    private final String initialSearch;
    private final String initialRepository;

    public FilterPullRequestsDialogConfiguration(List<CodeRepositoryDto> repos, PullRequestStatus currentStatus,
            String currentSearch, CodeRepositoryId currentRepo) {
        this.repositoryOptions = buildRepositoryOptions(repos);
        this.initialStatus = currentStatus != null
                ? STATUS_OPTIONS.entrySet().stream().filter(e -> e.getValue() == currentStatus).map(Map.Entry::getKey)
                        .findFirst().orElse(ALL)
                : ALL;
        this.initialSearch = currentSearch != null ? currentSearch : "";
        this.initialRepository = currentRepo != null
                ? repos.stream().filter(r -> r.id().equals(currentRepo.value())).map(r -> r.owner() + "/" + r.name())
                        .findFirst().orElse(ALL)
                : ALL;
    }

    @Override
    public List<Field> fields() {
        List<String> statusOptions = new ArrayList<>();
        statusOptions.add(ALL);
        statusOptions.addAll(STATUS_OPTIONS.keySet().stream().sorted().toList());

        return List.of(new SelectField(FilterFormFields.REPOSITORY, "Repository", repositoryOptions, initialRepository),
                new SelectField(FilterFormFields.STATUS, "Status", statusOptions, initialStatus),
                new TextField(FilterFormFields.SEARCH, "Search", false, initialSearch));
    }

    @Override
    public String title() {
        return "Filter Pull Requests";
    }

    @Override
    public String description() {
        return "Filter the pull request list.";
    }

    private static List<String> buildRepositoryOptions(List<CodeRepositoryDto> repos) {
        List<String> options = new ArrayList<>();
        options.add(ALL);
        repos.forEach(r -> options.add(r.owner() + "/" + r.name()));
        return options;
    }
}
