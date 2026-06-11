package com.pullrequesttracker.testfixtures.domain.model;

import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;

public class TokenTestBuilder {
    private TokenId id = TokenId.create();
    private TokenName name = TokenName.from("default-name");
    private TokenValue value = new TokenValue("default-value");
    private Platform platform = Platform.GITHUB;
    private TokenUsername username = new TokenUsername("default-user");

    public static TokenTestBuilder aToken() {
        return new TokenTestBuilder();
    }

    public TokenTestBuilder withId(TokenId id) {
        this.id = id;
        return this;
    }

    public TokenTestBuilder withName(TokenName name) {
        this.name = name;
        return this;
    }

    public TokenTestBuilder withValue(TokenValue value) {
        this.value = value;
        return this;
    }

    public TokenTestBuilder withPlatform(Platform platform) {
        this.platform = platform;
        return this;
    }

    public TokenTestBuilder withUsername(TokenUsername username) {
        this.username = username;
        return this;
    }

    public Token build() {
        return new Token(id, name, value, platform, username);
    }
}
