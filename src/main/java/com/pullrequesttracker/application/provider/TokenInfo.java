package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.domain.valueobject.TokenExpirationDate;
import com.pullrequesttracker.domain.valueobject.TokenUsername;

import java.util.Objects;

public record TokenInfo(TokenUsername username, TokenExpirationDate expirationDate) {
    public TokenInfo {
        Objects.requireNonNull(username, "Username must not be null");
        Objects.requireNonNull(expirationDate, "Expiration date must not be null");
    }
}
