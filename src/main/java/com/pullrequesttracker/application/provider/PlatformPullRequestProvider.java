package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.Platform;

import java.util.List;

public interface PlatformPullRequestProvider {
    Platform platform();
    List<PullRequestSyncData> fetch(CodeRepository codeRepository);
}
