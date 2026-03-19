package com.prtracker.application.repository;

import com.prtracker.application.dto.TokenProjection;

import java.util.List;
import java.util.Optional;

public interface TokenReadRepository {
    List<TokenProjection> findAllAsViews();

    Optional<TokenProjection> findViewById(String id);
}
