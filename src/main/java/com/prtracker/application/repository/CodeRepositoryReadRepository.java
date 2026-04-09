package com.prtracker.application.repository;

import com.prtracker.application.query.dto.CodeRepositoryProjection;

import java.util.List;

public interface CodeRepositoryReadRepository {
    List<CodeRepositoryProjection> findAllAsViews();
}
