package com.prtracker.domain.service;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.exceptions.TokenAlreadyExistsException;
import com.prtracker.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {
    private final TokenRepository tokenRepository;

    public void add(Token token) {
        if (tokenRepository.existsByName(token.getName())) {
            throw new TokenAlreadyExistsException(token.getName());
        }

        tokenRepository.save(token);
    }
}
