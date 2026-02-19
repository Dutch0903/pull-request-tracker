package com.prtracker.infrastructure.scheduling;

import com.prtracker.application.usecase.CheckRepositoriesUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryCheckScheduler {

    private final CheckRepositoriesUseCase checkRepositoriesUseCase;

    public void checkAllRepositories() {
        checkRepositoriesUseCase.execute();
    }
}
