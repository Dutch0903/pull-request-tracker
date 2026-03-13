package com.prtracker.domain.entity;

import com.prtracker.domain.valueobject.TokenId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Token {
	private TokenId id;
	private String name;
	private String value;
}
