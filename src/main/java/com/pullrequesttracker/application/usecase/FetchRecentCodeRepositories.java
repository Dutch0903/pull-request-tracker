package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchRecentCodeRepositories {
    private final CodeRepositoryRepository codeRepositoryRepository;

    public List<CodeRepositoryDto> execute() {
        return codeRepositoryRepository.findAll().stream()
                .map(r -> new CodeRepositoryDto(r.getId().value(), r.getFullName().owner(), r.getFullName().name()))
                .toList();
    }
}
