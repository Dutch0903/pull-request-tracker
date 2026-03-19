package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.domain.valueobject.TokenName;
import com.prtracker.infrastructure.persistence.dto.TokenDto;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public TokenDto toDto(Token token) {
        return new TokenDto(token.getId().value(), token.getName().value(), token.getValue());
    }

    public Token toDomain(TokenDto tokenDto) {
        return new Token(TokenId.from(tokenDto.id()), TokenName.from(tokenDto.name()), tokenDto.value());
    }
}
