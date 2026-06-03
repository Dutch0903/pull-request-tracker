package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchAllTokens {
    private final TokenRepository tokenRepository;

    public List<TokenDto> execute() {
        return tokenRepository.findAll().stream()
                .map(t -> new TokenDto(t.id().value(), t.name().toString(), t.value().toString()))
                .toList();
    }
}
