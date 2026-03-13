package com.prtracker.domain.repository;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.valueobject.TokenId;

import java.io.IOException;
import java.util.List;

public interface TokenRepository {
	void save(Token token);

	void delete(TokenId tokenId);

	List<Token> findAll();

	void initialize() throws IOException;

	void persist() throws IOException;
}
