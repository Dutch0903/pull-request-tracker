package com.prtracker.token.domain.service;

import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.domain.exception.TokenAlreadyExistsException;
import com.prtracker.token.domain.exception.TokenNotFoundException;
import com.prtracker.token.domain.model.Token;
import com.prtracker.token.domain.port.TokenRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class TokenDomainService {
    private final TokenRepository tokenRepository;

    public void create(Token token) {
        if (tokenRepository.existsByName(token.getName())) {
            throw new TokenAlreadyExistsException(token.getName());
        }

        tokenRepository.save(token);
    }

    public void update(Token token) {
        Optional<Token> result = tokenRepository.findById(token.getId());

        if (result.isEmpty()) {
            throw new TokenNotFoundException(token.getId());
        }

        Token existingToken = result.get();

        if (!existingToken.getName().equals(token.getName()) && tokenRepository.existsByName(token.getName())) {
            throw new TokenAlreadyExistsException(token.getName());
        }

        tokenRepository.save(token);
    }

    public void delete(TokenId tokenId) {
        tokenRepository.delete(tokenId);
    }
}
