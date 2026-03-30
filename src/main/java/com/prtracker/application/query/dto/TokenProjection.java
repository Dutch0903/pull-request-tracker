package com.prtracker.application.query.dto;

import com.prtracker.domain.entity.Token;

import java.util.UUID;

public record TokenProjection(UUID id, String name, String value) {
    public static TokenProjection from(Token token) {
        return new TokenProjection(token.getId().value(), token.getName().value(), token.getValue());
    }
}
