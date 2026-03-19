package com.prtracker.domain.entity;

import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.domain.valueobject.TokenName;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {
    private TokenId id;
    private TokenName name;
    private String value;
}
