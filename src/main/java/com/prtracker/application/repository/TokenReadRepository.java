package com.prtracker.application.repository;

import com.prtracker.application.query.dto.TokenProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenReadRepository {
    List<TokenProjection> findAllAsProjection();
}
