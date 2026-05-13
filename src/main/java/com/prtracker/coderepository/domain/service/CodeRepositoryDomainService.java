package com.prtracker.coderepository.domain.service;

import com.prtracker.coderepository.domain.exception.CodeRepositoryAlreadyExistsException;
import com.prtracker.coderepository.domain.exception.TokenNotFoundException;
import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.port.CodeRepositoryRepository;
import com.prtracker.coderepository.domain.port.TokenExistencePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CodeRepositoryDomainService {
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final TokenExistencePort tokenExistencePort;

    public void add(CodeRepository codeRepository) {
        if (codeRepositoryRepository.exists(codeRepository.getFullName())) {
            throw new CodeRepositoryAlreadyExistsException(codeRepository.getFullName());
        }

        if (codeRepository.getTokenId() != null && !tokenExistencePort.exists(codeRepository.getTokenId())) {
            throw new TokenNotFoundException(codeRepository.getTokenId());
        }

        codeRepositoryRepository.save(codeRepository);
    }
}
