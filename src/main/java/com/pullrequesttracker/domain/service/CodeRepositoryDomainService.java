package com.pullrequesttracker.domain.service;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodeRepositoryDomainService {
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final TokenRepository tokenRepository;

    public void add(CodeRepository codeRepository) {
        if (codeRepositoryRepository.exists(codeRepository.getFullName())) {
            throw new IllegalStateException("Repository already exists: " + codeRepository.getFullName());
        }

        if (codeRepository.getTokenId() != null && tokenRepository.findById(codeRepository.getTokenId()).isEmpty()) {
            throw new IllegalStateException("Token not found: " + codeRepository.getTokenId());
        }

        codeRepositoryRepository.save(codeRepository);
    }
}
