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
public class CreateToken {
    private final TokenRepository tokenRepository;

    public void execute(TokenName name, TokenValue value) {
        if (tokenRepository.existsByName(name)) {
            throw new IllegalStateException("Token already exists with name: " + name);
        }
        tokenRepository.save(new Token(TokenId.create(), name, value));
    }
}
