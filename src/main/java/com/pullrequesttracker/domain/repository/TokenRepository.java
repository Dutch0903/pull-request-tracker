package com.pullrequesttracker.domain.repository;

import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;

import java.util.List;
import java.util.Optional;

public interface TokenRepository {
    void save(Token token);
    void delete(TokenId tokenId);
    Optional<Token> findById(TokenId tokenId);
    Optional<TokenValue> findTokenValue(TokenId tokenId);
    boolean existsByName(TokenName name);
    List<Token> findAll();
}
