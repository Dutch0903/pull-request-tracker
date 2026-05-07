package com.prtracker.coderepository.domain.port;

import com.prtracker.coderepository.domain.model.CodeRepository;

import java.util.concurrent.CompletableFuture;

public interface RepositoryCheckerPort {
    CompletableFuture<Void> check(CodeRepository codeRepository);
}
