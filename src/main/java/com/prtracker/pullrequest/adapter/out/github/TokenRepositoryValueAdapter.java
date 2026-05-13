package com.prtracker.pullrequest.adapter.out.github;

import com.prtracker.pullrequest.domain.port.TokenValuePort;
import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.domain.port.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenRepositoryValueAdapter implements TokenValuePort {
    private final TokenRepository tokenRepository;

    @Override
    public String getTokenValue(TokenId tokenId) {
        return tokenRepository.findById(tokenId)
                .orElseThrow(() -> new GitHubApiException("Token not found: " + tokenId.value(), null)).getValue();
    }
}
