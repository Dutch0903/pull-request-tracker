package com.prtracker.infrastructure.scheduling;

import com.prtracker.application.command.CheckRepositoriesCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryCheckScheduler {

    private final CheckRepositoriesCommand checkRepositoriesCommand;

    public void checkAllRepositories() {
        checkRepositoriesCommand.execute();
    }
}
