package com.prtracker.infrastructure;

import com.prtracker.application.usecase.InitializeCodeRepositoriesUseCase;
import com.prtracker.application.usecase.PersistCodeRepositoriesUseCase;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LifeCycleManager {
    private final InitializeCodeRepositoriesUseCase initializeRepositoriesUseCase;
    private final PersistCodeRepositoriesUseCase persistCodeRepositoriesUseCase;

    @PostConstruct
    public void onPostConstruct() {
        initializeRepositoriesUseCase.execute();
    }

    @PreDestroy
    public void onPreDestroy() {
        persistCodeRepositoriesUseCase.execute();
    }
}
