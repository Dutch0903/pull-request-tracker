package com.pullrequesttracker.presentation.cli.view.pullrequest.component;

import com.pullrequesttracker.presentation.cli.view.pullrequest.PullRequestListState;
import com.pullrequesttracker.presentation.cli.view.pullrequest.filter.FilterDefinition;
import dev.tamboui.toolkit.element.Element;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.tamboui.toolkit.Toolkit.*;

@Component
@RequiredArgsConstructor
public class FilterBar {
    private final List<FilterDefinition> filterDefinitions;

    public Element render(PullRequestListState state) {
        List<String> parts = filterDefinitions.stream().filter(def -> def.isActive(state.getFilterValue(def.fieldId())))
                .map(def -> def.label() + ": " + def.formatDisplayValue(state.getFilterValue(def.fieldId()))).toList();

        if (parts.isEmpty()) {
            return spacer(0);
        }

        return column(text("  Filters:  " + String.join("  ·  ", parts)).dim());
    }
}
