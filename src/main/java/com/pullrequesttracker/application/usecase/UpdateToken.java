package com.pullrequesttracker.application.usecase;

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

    public void execute(TokenId id, TokenName name, TokenValue value) {
        Token existing = tokenRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Token not found: " + id));

        if (!existing.name().equals(name) && tokenRepository.existsByName(name)) {
            throw new IllegalStateException("Token already exists with name: " + name);
        }

        tokenRepository.save(new Token(id, name, value));
    }
}
