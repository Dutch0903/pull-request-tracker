package com.prtracker.infrastructure.persistence;

import com.prtracker.application.dto.TokenView;
import com.prtracker.application.repository.TokenReadRepository;
import com.prtracker.domain.entity.Token;
import com.prtracker.domain.repository.TokenRepository;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.infrastructure.persistence.dto.TokenDto;
import com.prtracker.infrastructure.persistence.mapper.TokenMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryTokenRepository implements TokenRepository, TokenReadRepository {
	private static final String FILE_NAME = "tokens.json";
	private final FileStorage fileStorage;
	private final TokenMapper mapper;

	private final ConcurrentHashMap<TokenId, Token> tokens = new ConcurrentHashMap<>();

	// Domain repository methods (for commands)
	@Override
	public void save(Token token) {
		tokens.put(token.getId(), token);
	}

	@Override
	public void delete(TokenId tokenId) {
		tokens.remove(tokenId);
	}

	@Override
	public List<Token> findAll() {
		return List.copyOf(tokens.values());
	}

	// Read repository methods (for queries)
	@Override
	public List<TokenView> findAllAsViews() {
		return tokens.values().stream().map(TokenView::from).toList();
	}

	@Override
	public Optional<TokenView> findViewById(String id) {
		try {
			UUID uuid = UUID.fromString(id);
			TokenId tokenId = TokenId.from(uuid);
			return Optional.ofNullable(tokens.get(tokenId)).map(TokenView::from);
		} catch (IllegalArgumentException e) {
			return Optional.empty();
		}
	}

	@Override
	public void initialize() {
		List<TokenDto> loadedTokens = fileStorage.load(FILE_NAME, TokenDto.class);

		tokens.putAll(loadedTokens.stream().map(mapper::toDomain)
				.collect(Collectors.toMap(Token::getId, Function.identity())));
	}

	@Override
	public void persist() throws IOException {
		fileStorage.save(FILE_NAME, tokens.values().stream().map(mapper::toDto).toList());
	}
}
