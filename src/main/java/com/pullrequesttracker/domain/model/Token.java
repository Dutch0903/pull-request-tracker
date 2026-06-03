package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;

import java.util.Objects;

public record Token(TokenId id, TokenName name, TokenValue value) {
    public Token {
        Objects.requireNonNull(id, "Token id must not be null");
        Objects.requireNonNull(name, "Token name must not be null");
        Objects.requireNonNull(value, "Token value must not be null");
    }
}
