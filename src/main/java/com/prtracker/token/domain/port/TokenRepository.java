package com.prtracker.token.domain.port;

import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.domain.model.Token;
import com.prtracker.token.domain.model.TokenName;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    void save(Token token);

    void delete(TokenId tokenId);

    int count();

    Optional<Token> findById(TokenId tokenId);

    List<Token> findAll();

    boolean existsByName(TokenName name);

    void initialize() throws IOException;

    void persist() throws IOException;
}
