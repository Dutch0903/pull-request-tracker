package com.prtracker.domain.valueobject;

import java.util.UUID;

public record TokenId(UUID value) {
	public static TokenId from(UUID id) {
		return new TokenId(id);
	}

	public static TokenId create() {
		return new TokenId(UUID.randomUUID());
	}
}
