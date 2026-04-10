package com.prtracker.testfixtures.infrastructure.persistence.dto;

import com.prtracker.infrastructure.persistence.dto.TokenDto;

import java.util.UUID;

public class TokenDtoTestBuilder {
    private UUID id = UUID.randomUUID();
    private String name = "name";
    private String value = "value";

    public static TokenDtoTestBuilder aTokenDto() {
        return new TokenDtoTestBuilder();
    }

    public TokenDtoTestBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public TokenDtoTestBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TokenDtoTestBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public TokenDto build() {
        return new TokenDto(id, name, value);
    }
}
