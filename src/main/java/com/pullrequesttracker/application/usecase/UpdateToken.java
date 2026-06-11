package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.provider.TokenInfo;
import com.pullrequesttracker.application.provider.TokenInfoProvider;
import com.pullrequesttracker.domain.exception.DuplicateTokenNameException;
import com.pullrequesttracker.domain.exception.TokenNotFoundException;
import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateToken {
    private final TokenRepository tokenRepository;
    private final TokenInfoProvider tokenInfoProvider;

    public void execute(TokenId id, TokenName name, TokenValue value) {
        Token existing = tokenRepository.findById(id)
                .orElseThrow(() -> new TokenNotFoundException("Token not found: " + id));

        if (!existing.name().equals(name) && tokenRepository.existsByName(name)) {
            throw new DuplicateTokenNameException("Token already exists with name: " + name);
        }

        TokenInfo info = tokenInfoProvider.fetchTokenInfo(existing.platform(), value);
        tokenRepository.save(new Token(id, name, value, existing.platform(), info.username(), info.expirationDate()));
    }
}
