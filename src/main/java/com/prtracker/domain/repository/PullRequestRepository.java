package com.prtracker.domain.repository;

import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.valueobject.PullRequestId;

import java.util.List;
import java.util.Optional;

public interface PullRequestRepository {
    void save(PullRequest pullRequest);

    void delete(PullRequest pullRequest);

    Optional<PullRequest> findById(PullRequestId id);

    List<PullRequest> findAll();

    List<PullRequest> findAllByRepository(CodeRepository repository);

    void initialize();

    void persist();
}
