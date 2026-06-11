package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.provider.UserProvider;
import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateToken {
    private final TokenRepository tokenRepository;
    private final UserProvider userProvider;

    public void execute(TokenName name, TokenValue value, Platform platform) {
        if (tokenRepository.existsByName(name)) {
            throw new IllegalStateException("Token already exists with name: " + name);
        }

        TokenUsername username = userProvider.fetchUsername(platform, value);
        tokenRepository.save(new Token(TokenId.create(), name, value, platform, username));
    }
}
