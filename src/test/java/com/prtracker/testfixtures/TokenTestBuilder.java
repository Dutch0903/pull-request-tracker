package com.prtracker.testfixtures;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.domain.valueobject.TokenName;

public class TokenTestBuilder {
    private TokenId id = TokenId.create();
    private TokenName name = TokenName.from("default-name");
    private String value = "default-value";

    public static TokenTestBuilder aToken() {
        return new TokenTestBuilder();
    }

    public static TokenTestBuilder copyOf(Token token) {
        return aToken()
                .withTokenId(token.getId())
                .withName(token.getName())
                .withValue(token.getValue());
    }

    public TokenTestBuilder withTokenId(TokenId id) {
        this.id = id;
        return this;
    }

    public TokenTestBuilder withName(TokenName name) {
        this.name = name;
        return this;
    }

    public TokenTestBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public Token build() {
        return new Token(id, name, value);
    }
}
