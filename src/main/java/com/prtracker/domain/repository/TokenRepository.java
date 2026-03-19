package com.prtracker.domain.repository;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.domain.valueobject.TokenName;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    void save(Token token);

    void delete(TokenId tokenId);

    Optional<Token> findById(TokenId tokenId);

    List<Token> findAll();

    boolean existsByName(TokenName name);

    void initialize() throws IOException;

    void persist() throws IOException;
}
