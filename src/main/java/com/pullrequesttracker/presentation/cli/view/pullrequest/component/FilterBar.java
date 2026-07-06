package com.pullrequesttracker.presentation.cli.view.pullrequest.component;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.application.usecase.FetchAllCodeRepositories;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListState;
import dev.tamboui.toolkit.element.Element;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static dev.tamboui.toolkit.Toolkit.*;

@Component
@RequiredArgsConstructor
public class FilterBar {
    private final FetchAllCodeRepositories fetchAllCodeRepositories;

    public Element render(PullRequestListState state) {
        List<String> parts = new ArrayList<>();

        if (state.getStatusFilter() != null) {
            parts.add("Status: " + formatStatus(state.getStatusFilter().name()));
        }
        if (state.getSearchQuery() != null && !state.getSearchQuery().isBlank()) {
            parts.add("Search: \"" + state.getSearchQuery() + "\"");
        }
        if (state.getCodeRepositoryFilter() != null) {
            parts.add("Repo: " + resolveRepoName(state.getCodeRepositoryFilter()));
        }

        if (parts.isEmpty()) {
            return spacer(0);
        }

        String label = "Filters:  " + String.join("  ·  ", parts);
        return column(text("  " + label).dim());
    }

    private String formatStatus(String name) {
        if (name.isEmpty())
            return name;
        return name.charAt(0) + name.substring(1).toLowerCase();
    }

    private String resolveRepoName(CodeRepositoryId repoId) {
        return fetchAllCodeRepositories.execute().stream().filter(r -> r.id().equals(repoId.value()))
                .map(CodeRepositoryDto::name).findFirst().orElse(repoId.value().toString());
    }
}
