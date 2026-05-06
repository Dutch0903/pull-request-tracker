package com.prtracker.coderepository.adapter.out.token;

import com.prtracker.coderepository.domain.port.TokenExistencePort;
import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.domain.port.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRepositoryExistenceAdapter implements TokenExistencePort {
    private final TokenRepository tokenRepository;

    @Override
    public boolean exists(TokenId tokenId) {
        return tokenRepository.findById(tokenId).isPresent();
    }
}
