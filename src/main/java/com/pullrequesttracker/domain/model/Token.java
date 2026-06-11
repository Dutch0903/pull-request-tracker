package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenExpirationDate;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;

import java.util.Objects;

public record Token(TokenId id, TokenName name, TokenValue value, Platform platform, TokenUsername username,
        TokenExpirationDate expirationDate) {
    public Token {
        Objects.requireNonNull(id, "Token id must not be null");
        Objects.requireNonNull(name, "Token name must not be null");
        Objects.requireNonNull(value, "Token value must not be null");
        Objects.requireNonNull(platform, "Token platform must not be null");
        Objects.requireNonNull(username, "Token username must not be null");
        Objects.requireNonNull(expirationDate, "Token expiration date must not be null");
    }
}
