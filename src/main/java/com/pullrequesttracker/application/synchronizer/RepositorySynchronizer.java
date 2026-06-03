package com.pullrequesttracker.application.synchronizer;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;

import java.util.List;

public interface RepositorySynchronizer {
    List<PullRequestSyncData> synchronize(CodeRepository codeRepository);
}
