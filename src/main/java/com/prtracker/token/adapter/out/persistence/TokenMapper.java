package com.prtracker.token.adapter.out.persistence;

import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.domain.model.Token;
import com.prtracker.token.domain.model.TokenName;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {
    public TokenDto toDto(Token token) {
        return new TokenDto(token.getId().value(), token.getName().value(), token.getValue());
    }

    public Token toDomain(TokenDto dto) {
        return new Token(TokenId.from(dto.id()), TokenName.from(dto.name()), dto.value());
    }
}
