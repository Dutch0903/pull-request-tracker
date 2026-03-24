package com.prtracker.application.query.dto;

import com.prtracker.domain.entity.Token;

import java.util.UUID;

public record TokenProjection(UUID id, String name) {
    public static TokenProjection from(Token token) {
        return new TokenProjection(token.getId().value(), token.getName().value());
    }
}
