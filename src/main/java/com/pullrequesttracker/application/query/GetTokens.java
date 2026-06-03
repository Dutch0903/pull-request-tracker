package com.pullrequesttracker.application.query;

import com.pullrequesttracker.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetTokens {
    private final TokenRepository tokenRepository;

    public List<TokenProjection> execute() {
        return tokenRepository.findAll().stream()
                .map(t -> new TokenProjection(t.id().value(), t.name().toString(), t.value().toString()))
                .toList();
    }
}
