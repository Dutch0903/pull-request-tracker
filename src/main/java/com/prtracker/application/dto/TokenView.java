package com.prtracker.application.dto;

import com.prtracker.domain.entity.Token;

public record TokenView(String id, String name, String value) {
    public static TokenView from(Token token) {
        return new TokenView(token.getId().value().toString(), token.getName(), token.getValue());
    }
}
