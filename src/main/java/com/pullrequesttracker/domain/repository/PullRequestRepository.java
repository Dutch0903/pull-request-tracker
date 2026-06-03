package com.pullrequesttracker.domain.repository;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;

import java.util.List;

public interface PullRequestRepository {
    void save(PullRequest pullRequest);
    List<PullRequest> findAllByCodeRepositoryId(CodeRepositoryId codeRepositoryId);
}
