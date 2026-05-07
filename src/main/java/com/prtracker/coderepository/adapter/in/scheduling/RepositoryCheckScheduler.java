package com.prtracker.coderepository.adapter.in.scheduling;

import com.prtracker.coderepository.application.command.CheckRepositories;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RepositoryCheckScheduler {
    private final CheckRepositories checkRepositories;

    @Scheduled(fixedDelayString = "${repository-check.interval-ms}")
    public void checkAllRepositories() {
        log.info("Checking all repository repositories");
        checkRepositories.execute();
    }
}
