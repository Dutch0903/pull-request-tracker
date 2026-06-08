package com.pullrequesttracker.domain.repository;

import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;

import java.util.List;
import java.util.Map;

public interface PullRequestRepository {
    void save(PullRequest pullRequest);
    List<PullRequest> findAllByCodeRepositoryId(CodeRepositoryId codeRepositoryId);
    Map<CodeRepositoryId, Integer> countAllByCodeRepositoryId();
    List<PullRequest> findAll();
    List<PullRequest> findAllOpen();
}
