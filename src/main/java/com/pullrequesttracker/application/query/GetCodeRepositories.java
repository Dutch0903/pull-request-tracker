package com.pullrequesttracker.application.query;

import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCodeRepositories {
    private final CodeRepositoryRepository codeRepositoryRepository;

    public List<CodeRepositoryProjection> execute() {
        return codeRepositoryRepository.findAll().stream()
                .map(r -> new CodeRepositoryProjection(r.getId().value(), r.getFullName().owner(), r.getFullName().name()))
                .toList();
    }
}
