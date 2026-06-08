package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.CodeRepositorySummaryDto;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class FetchCodeRepositorySummaries {
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final PullRequestRepository pullRequestRepository;

    public List<CodeRepositorySummaryDto> execute() {
        List<CodeRepository> codeRepositories = codeRepositoryRepository.findAll();
        Map<CodeRepositoryId, Integer> countAllByCodeRepositoryId = pullRequestRepository.countAllByCodeRepositoryId();

        return codeRepositories.stream()
                .map(codeRepository -> new CodeRepositorySummaryDto(codeRepository.getFullName().toString(),
                        countAllByCodeRepositoryId.getOrDefault(codeRepository.getId(), 0)))
                .toList();
    }
}
