package com.prtracker.application.repository;

import com.prtracker.application.query.dto.TokenProjection;

import java.util.List;

public interface TokenReadRepository {
    List<TokenProjection> findAllAsProjection();
}
