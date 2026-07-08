package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.repository.PullRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchAllAuthors {
    private final PullRequestRepository pullRequestRepository;

    public List<String> execute() {
        return pullRequestRepository.findAllAuthors();
    }
}
