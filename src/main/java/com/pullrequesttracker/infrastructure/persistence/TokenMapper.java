package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenExpirationDate;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import com.pullrequesttracker.infrastructure.persistence.dto.TokenDto;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Optional;

@Component
public class TokenMapper {

    public TokenDto toDto(Token token) {
        return new TokenDto(token.id().value(), token.name().toString(), token.value().toString(),
                token.platform().name(), token.username().value(), token.expirationDate().value().toString());
    }

    public Optional<Token> toDomain(TokenDto dto) {
        if (dto.platform() == null || dto.username() == null || dto.expirationDate() == null) {
            return Optional.empty();
        }
        return Optional.of(new Token(new TokenId(dto.id()), new TokenName(dto.name()), new TokenValue(dto.value()),
                Platform.valueOf(dto.platform()), new TokenUsername(dto.username()),
                new TokenExpirationDate(Instant.parse(dto.expirationDate()))));
    }
}
