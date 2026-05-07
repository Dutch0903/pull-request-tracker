package com.prtracker.coderepository.application.command;

import com.prtracker.coderepository.domain.port.CodeRepositoryRepository;
import com.prtracker.coderepository.domain.port.RepositoryCheckerPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckRepositories {
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final RepositoryCheckerPort repositoryCheckerPort;

    public void execute() {
        List<CompletableFuture<Void>> futures = codeRepositoryRepository.findAll()
                .stream()
                .map(repo -> {
                    log.info("Checking for repository {}", repo);
                    return repositoryCheckerPort.check(repo);
                })
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
