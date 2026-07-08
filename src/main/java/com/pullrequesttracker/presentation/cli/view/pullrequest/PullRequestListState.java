package com.pullrequesttracker.presentation.cli.view.pullrequest;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.presentation.cli.state.SnapshotKey;
import com.pullrequesttracker.presentation.cli.state.StateManager;
import com.pullrequesttracker.presentation.cli.view.pullrequest.filter.FilterDefinition;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PullRequestListState extends StateManager {
    public static final SnapshotKey<List<PullRequestListItemDto>> PULL_REQUEST_ITEMS = new SnapshotKey<>(
            "pullRequestItems", Duration.ofMinutes(1));

    private final Map<String, String> activeFilterValues = new HashMap<>();

    public PullRequestListState(List<FilterDefinition> filterDefinitions) {
        filterDefinitions.forEach(def -> {
            if (def.defaultValue() != null) {
                activeFilterValues.put(def.fieldId(), def.defaultValue());
            }
        });
    }

    public String getFilterValue(String fieldId) {
        return activeFilterValues.get(fieldId);
    }

    public void setFilterValue(String fieldId, String value) {
        if (value == null) {
            activeFilterValues.remove(fieldId);
        } else {
            activeFilterValues.put(fieldId, value);
        }
    }

    public boolean hasActiveFilters() {
        return !activeFilterValues.isEmpty();
    }

    public void resetFilters() {
        activeFilterValues.clear();
    }
}
