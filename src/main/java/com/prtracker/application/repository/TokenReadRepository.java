package com.prtracker.application.repository;

import com.prtracker.application.dto.TokenView;

import java.util.List;
import java.util.Optional;

public interface TokenReadRepository {
    List<TokenView> findAllAsViews();

    Optional<TokenView> findViewById(String id);
}
