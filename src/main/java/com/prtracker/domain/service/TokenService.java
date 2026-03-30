package com.prtracker.domain.service;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.exceptions.TokenAlreadyExistsException;
import com.prtracker.domain.exceptions.TokenNotFoundException;
import com.prtracker.domain.repository.TokenRepository;
import com.prtracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService {
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
