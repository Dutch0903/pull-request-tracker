package com.prtracker.token.application.query;

import com.prtracker.token.domain.model.Token;

import java.util.UUID;

public record TokenProjection(UUID id, String name, String value) {
    public static TokenProjection from(Token token) {
        return new TokenProjection(token.getId().value(), token.getName().value(), token.getValue());
    }
}
