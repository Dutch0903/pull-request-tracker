package com.prtracker.pullrequest.domain.port;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.pullrequest.domain.model.PullRequestSyncData;

import java.util.List;

public interface RepositorySynchronizerPort {
    List<PullRequestSyncData> synchronize(CodeRepository codeRepository);
}
