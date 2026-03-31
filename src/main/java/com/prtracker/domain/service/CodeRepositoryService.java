package com.prtracker.domain.service;

import com.prtracker.application.repository.TokenReadRepository;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.exceptions.CodeRepositoryAlreadyExistsException;
import com.prtracker.domain.exceptions.TokenNotFoundException;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import com.prtracker.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeRepositoryService {
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final TokenRepository tokenRepository;

    public void add(CodeRepository codeRepository) {
        if (codeRepositoryRepository.exists(codeRepository.getIdentifier())) {
            throw new CodeRepositoryAlreadyExistsException(codeRepository.getIdentifier());
        }

        if (tokenRepository.findById(codeRepository.getTokenId()).isEmpty()) {
            throw new TokenNotFoundException(codeRepository.getTokenId());
        }

        codeRepositoryRepository.save(codeRepository);
    }
}
