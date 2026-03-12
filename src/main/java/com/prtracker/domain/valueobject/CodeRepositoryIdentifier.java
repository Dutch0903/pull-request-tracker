package com.prtracker.domain.valueobject;

public record CodeRepositoryIdentifier(String value) {
	public static CodeRepositoryIdentifier from(String identifier) {
		return new CodeRepositoryIdentifier(identifier);
	}
	public static CodeRepositoryIdentifier from(String owner, String name) {
		return new CodeRepositoryIdentifier(owner + "/" + name);
	}
}
