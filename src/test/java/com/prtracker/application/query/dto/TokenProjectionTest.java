package com.prtracker.application.query.dto;

import com.prtracker.domain.entity.Token;
import org.junit.jupiter.api.Test;

import static com.prtracker.testfixtures.TokenTestBuilder.aToken;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenProjectionTest {
    @Test
    void from_whenCalled_shouldMapValuesCorrectly() {
        Token token = aToken().build();

        TokenProjection projection = TokenProjection.from(token);

        assertEquals(token.getId().value(), projection.id());
        assertEquals(token.getName().value(), projection.name());
    }
}
