package com.pullrequesttracker.domain.service;

import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenDomainService {
    private final TokenRepository tokenRepository;

    public void create(Token token) {
        if (tokenRepository.existsByName(token.name())) {
            throw new IllegalStateException("Token already exists with name: " + token.name());
        }

        tokenRepository.save(token);
    }

    public void update(Token token) {
        Token existing = tokenRepository.findById(token.id())
                .orElseThrow(() -> new IllegalStateException("Token not found: " + token.id()));

        if (!existing.name().equals(token.name()) && tokenRepository.existsByName(token.name())) {
            throw new IllegalStateException("Token already exists with name: " + token.name());
        }

        tokenRepository.save(token);
    }

    public void delete(TokenId tokenId) {
        tokenRepository.delete(tokenId);
    }
}
