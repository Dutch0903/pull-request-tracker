package com.pullrequesttracker.testfixtures.domain.valueobject;

import com.pullrequesttracker.domain.valueobject.TokenExpirationDate;

import java.time.Instant;

public class TokenExpirationDateTestBuilder {
    private Instant value = Instant.now();

    public static TokenExpirationDateTestBuilder aTokenExpirationDate() {
        return new TokenExpirationDateTestBuilder();
    }

    public TokenExpirationDateTestBuilder withValue(Instant value) {
        this.value = value;
        return this;
    }

    public TokenExpirationDate build() {
        return TokenExpirationDate.from(value);
    }
}
