package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.infrastructure.persistence.dto.TokenDto;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public TokenDto toDto(Token token) {
        return new TokenDto(token.getId().value(), token.getName(), token.getValue());
    }

    public Token toDomain(TokenDto tokenDto) {
        return new Token(TokenId.from(tokenDto.id()), tokenDto.name(), tokenDto.value());
    }
}
