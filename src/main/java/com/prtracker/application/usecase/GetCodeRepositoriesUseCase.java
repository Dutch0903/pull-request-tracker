package com.prtracker.application.usecase;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCodeRepositoriesUseCase extends AbstractUseCase<Void, List<CodeRepository>> {
    private final CodeRepositoryRepository codeRepositoryRepository;

    @Override
    protected List<CodeRepository> executeInternal(Void input) {
        return codeRepositoryRepository.findAll();
    }
}
