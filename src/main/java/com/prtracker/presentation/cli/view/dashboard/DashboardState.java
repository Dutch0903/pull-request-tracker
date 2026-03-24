package com.prtracker.presentation.cli.view.dashboard;

import com.prtracker.application.query.dto.CodeRepositoryProjection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DashboardState {
    private List<CodeRepositoryProjection> recentRepositories = new ArrayList<>();
    private int selectedRepoIndex = 0;

    public void setRecentRepositories(List<CodeRepositoryProjection> repositories) {
        this.recentRepositories = new ArrayList<>(repositories);
        this.selectedRepoIndex = Math.min(selectedRepoIndex, repositories.size() - 1);
    }

    public List<CodeRepositoryProjection> getRecentRepositories() {
        return new ArrayList<>(recentRepositories);
    }

    public int getSelectedRepoIndex() {
        return selectedRepoIndex;
    }

    public void moveRepoSelection(int delta) {
        if (recentRepositories.isEmpty())
            return;
        selectedRepoIndex = Math.max(0, Math.min(recentRepositories.size() - 1, selectedRepoIndex + delta));
    }

    public CodeRepositoryProjection getSelectedRepository() {
        if (recentRepositories.isEmpty())
            return null;
        return recentRepositories.get(selectedRepoIndex);
    }
}
