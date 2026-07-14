package com.pullrequesttracker.presentation.cli.view.repository;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.application.dto.CodeRepositoryStatisticsDto;
import com.pullrequesttracker.presentation.cli.state.SnapshotKey;
import com.pullrequesttracker.presentation.cli.state.StateManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryListState extends StateManager {
    public static final SnapshotKey<List<CodeRepositoryDto>> REPOSITORIES = new SnapshotKey<>("repositories");

    public static final SnapshotKey<CodeRepositoryStatisticsDto> REPOSITORY_STATS = new SnapshotKey<>(
            "repositoryStats");
}
