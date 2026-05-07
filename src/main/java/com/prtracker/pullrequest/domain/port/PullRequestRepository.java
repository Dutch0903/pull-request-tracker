package com.prtracker.pullrequest.domain.port;

import com.prtracker.pullrequest.domain.model.PullRequest;
import com.prtracker.pullrequest.domain.model.PullRequestId;
import com.prtracker.shared.kernel.CodeRepositoryId;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PullRequestRepository {
    void save(PullRequest pullRequest);

    void delete(PullRequest pullRequest);

    int count();

    Optional<PullRequest> findById(PullRequestId id);

    List<PullRequest> findAll();

    List<PullRequest> findAllByCodeRepositoryId(CodeRepositoryId codeRepositoryId);

    void initialize() throws IOException;

    void persist() throws IOException;
}
