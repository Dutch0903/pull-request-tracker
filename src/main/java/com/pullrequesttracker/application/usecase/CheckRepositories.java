package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class CheckRepositories {
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final SynchronizeCodeRepository synchronizeCodeRepository;

    public void execute() {
        List<CompletableFuture<Void>> futures = codeRepositoryRepository.findAll().stream().map(repo -> {
            log.info("Checking {} repository {}/{}", repo.getPlatform(), repo.getFullName().owner(),
                    repo.getFullName().name());
            return synchronizeCodeRepository.execute(repo);
        }).toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }
}
