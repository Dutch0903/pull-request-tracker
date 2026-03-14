package com.prtracker.application.repository;

import com.prtracker.application.dto.CodeRepositoryView;

import java.util.List;
import java.util.Optional;

public interface CodeRepositoryReadRepository {
    List<CodeRepositoryView> findAllAsViews();

    Optional<CodeRepositoryView> findViewById(String identifier);
}
