package com.prtracker.token.application;

import com.prtracker.token.domain.port.TokenRepository;
import com.prtracker.token.domain.service.TokenDomainService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TokenDomainConfiguration {

    @Bean
    TokenDomainService tokenDomainService(TokenRepository tokenRepository) {
        return new TokenDomainService(tokenRepository);
    }
}
