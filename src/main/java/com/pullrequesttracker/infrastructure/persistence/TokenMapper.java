package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import com.pullrequesttracker.infrastructure.persistence.dto.TokenDto;
import org.springframework.stereotype.Component;

@Component
public class TokenMapper {

    public TokenDto toDto(Token token) {
        return new TokenDto(token.id().value(), token.name().toString(), token.value().toString(),
                token.platform().name(), token.username().value());
    }

    public Token toDomain(TokenDto dto) {
        return new Token(new TokenId(dto.id()), new TokenName(dto.name()), new TokenValue(dto.value()),
                Platform.valueOf(dto.platform()), new TokenUsername(dto.username()));
    }
}
