package com.pullrequesttracker.domain.valueobject;

import java.time.Instant;
import java.util.Objects;

public record TokenExpirationDate(Instant value) {
    public TokenExpirationDate {
        Objects.requireNonNull(value, "Token expiration date must not be null");
    }

    public static TokenExpirationDate from(Instant value) {
        return new TokenExpirationDate(value);
    }
}
