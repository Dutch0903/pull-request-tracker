package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DashboardState {
    private List<CodeRepositoryDto> recentRepositories = new ArrayList<>();
    private int selectedRepoIndex = 0;

    public void setRecentRepositories(List<CodeRepositoryDto> repositories) {
        this.recentRepositories = new ArrayList<>(repositories);
        this.selectedRepoIndex = Math.min(selectedRepoIndex, repositories.size() - 1);
    }

    public List<CodeRepositoryDto> getRecentRepositories() {
        return new ArrayList<>(recentRepositories);
    }

    public int getSelectedRepoIndex() {
        return selectedRepoIndex;
    }

    public void moveRepoSelection(int delta) {
        if (recentRepositories.isEmpty()) return;
        selectedRepoIndex = Math.max(0, Math.min(recentRepositories.size() - 1, selectedRepoIndex + delta));
    }

    public CodeRepositoryDto getSelectedRepository() {
        if (recentRepositories.isEmpty()) return null;
        return recentRepositories.get(selectedRepoIndex);
    }
}
