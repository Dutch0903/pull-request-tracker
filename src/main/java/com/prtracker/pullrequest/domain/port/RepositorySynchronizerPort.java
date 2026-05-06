package com.prtracker.pullrequest.domain.port;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.pullrequest.domain.model.PullRequest;

import java.util.List;

public interface RepositorySynchronizerPort {
    List<PullRequest> synchronize(CodeRepository codeRepository, List<PullRequest> existingPullRequests);
}
