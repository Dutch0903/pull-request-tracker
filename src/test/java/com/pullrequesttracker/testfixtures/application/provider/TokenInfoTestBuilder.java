package com.pullrequesttracker.testfixtures.application.provider;

import com.pullrequesttracker.application.provider.TokenInfo;
import com.pullrequesttracker.domain.valueobject.TokenExpirationDate;
import com.pullrequesttracker.domain.valueobject.TokenUsername;

import static com.pullrequesttracker.testfixtures.domain.valueobject.TokenExpirationDateTestBuilder.aTokenExpirationDate;
import static com.pullrequesttracker.testfixtures.domain.valueobject.TokenUsernameTestBuilder.aTokenUsername;

public class TokenInfoTestBuilder {
    private TokenUsername tokenUsername = aTokenUsername().build();
    private TokenExpirationDate tokenExpirationDate = aTokenExpirationDate().build();

    public static TokenInfoTestBuilder aTokenInfo() {
        return new TokenInfoTestBuilder();
    }

    public TokenInfoTestBuilder withTokenUsername(TokenUsername tokenUsername) {
        this.tokenUsername = tokenUsername;
        return this;
    }

    public TokenInfoTestBuilder withTokenExpirationDate(TokenExpirationDate tokenExpirationDate) {
        this.tokenExpirationDate = tokenExpirationDate;
        return this;
    }

    public TokenInfo build() {
        return new TokenInfo(tokenUsername, tokenExpirationDate);
    }
}
