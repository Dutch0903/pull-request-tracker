package com.prtracker.coderepository.adapter.in.scheduling;

import com.prtracker.coderepository.application.command.CheckRepositories;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryCheckScheduler {
    private final CheckRepositories checkRepositories;

    public void checkAllRepositories() {
        checkRepositories.execute();
    }
}
