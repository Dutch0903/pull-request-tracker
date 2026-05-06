package com.prtracker.token.domain.model;

import com.prtracker.shared.kernel.TokenId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {
    private TokenId id;
    private TokenName name;
    private String value;
}
