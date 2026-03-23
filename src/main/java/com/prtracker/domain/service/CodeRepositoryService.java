package com.prtracker.domain.service;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.exceptions.CodeRepositoryAlreadyExistsException;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeRepositoryService {
    private final CodeRepositoryRepository codeRepositoryRepository;

    public void add(CodeRepository codeRepository) {
        if (codeRepositoryRepository.exists(codeRepository.getIdentifier())) {
            throw new CodeRepositoryAlreadyExistsException(codeRepository.getIdentifier());
        }

        codeRepositoryRepository.save(codeRepository);
    }
}
