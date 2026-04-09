package com.prtracker.application.service;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.entity.PullRequest;

import java.util.List;

public interface PullRequestFetcher {
    List<PullRequest> fetchPullRequests(CodeRepository codeRepository);
}
