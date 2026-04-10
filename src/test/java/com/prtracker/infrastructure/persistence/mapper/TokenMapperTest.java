package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.domain.valueobject.TokenName;
import com.prtracker.infrastructure.persistence.dto.TokenDto;
import org.junit.jupiter.api.Test;

import static com.prtracker.testfixtures.domain.entity.TokenTestBuilder.aToken;
import static com.prtracker.testfixtures.infrastructure.persistence.dto.TokenDtoTestBuilder.aTokenDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenMapperTest {
    @Test
    void toDto_whenCalled_shouldMapValuesCorrectly() {
        TokenMapper mapper = new TokenMapper();

        Token token = aToken().build();

        TokenDto dto = mapper.toDto(token);

        assertEquals(token.getId().value(), dto.id());
        assertEquals(token.getName().value(), dto.name());
        assertEquals(token.getValue(), dto.value());
    }

    @Test
    void toDomain_whenCalled_shouldMapValuesCorrectly() {
        TokenMapper mapper = new TokenMapper();

        TokenDto dto = aTokenDto().build();

        Token token = mapper.toDomain(dto);

        assertEquals(TokenId.from(dto.id()), token.getId());
        assertEquals(TokenName.from(dto.name()), token.getName());
        assertEquals(dto.value(), token.getValue());
    }
}
