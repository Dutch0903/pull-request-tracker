package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.provider.TokenInfo;
import com.pullrequesttracker.application.provider.TokenInfoProvider;
import com.pullrequesttracker.domain.exception.DuplicateTokenNameException;
import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateToken {
    private final TokenRepository tokenRepository;
    private final TokenInfoProvider tokenInfoProvider;

    public void execute(TokenName name, TokenValue value, Platform platform) {
        if (tokenRepository.existsByName(name)) {
            throw new DuplicateTokenNameException("Token already exists with name: " + name);
        }

        TokenInfo info = tokenInfoProvider.fetchTokenInfo(platform, value);
        tokenRepository
                .save(new Token(TokenId.create(), name, value, platform, info.username(), info.expirationDate()));
    }
}
