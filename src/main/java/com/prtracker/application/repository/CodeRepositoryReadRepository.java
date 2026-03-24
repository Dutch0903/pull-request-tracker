package com.prtracker.application.repository;

import com.prtracker.application.query.dto.CodeRepositoryProjection;

import java.util.List;
import java.util.Optional;

public interface CodeRepositoryReadRepository {
    List<CodeRepositoryProjection> findAllAsViews();

    Optional<CodeRepositoryProjection> findViewById(String identifier);
}
