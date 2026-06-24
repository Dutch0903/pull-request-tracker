package com.pullrequesttracker.testfixtures.domain.valueobject;

import com.pullrequesttracker.domain.valueobject.TokenUsername;

public class TokenUsernameTestBuilder {
    private String value = "username";

    public static TokenUsernameTestBuilder aTokenUsername() {
        return new TokenUsernameTestBuilder();
    }

    public TokenUsernameTestBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public TokenUsername build() {
        return TokenUsername.from(value);
    }
}
