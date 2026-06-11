package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import com.pullrequesttracker.infrastructure.persistence.dto.TokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Slf4j
public class InMemoryTokenRepository implements TokenRepository {
    private static final String FILE_NAME = "tokens.json";
    private final FileStorage fileStorage;
    private final TokenMapper mapper;

    private final ConcurrentHashMap<TokenId, Token> tokens = new ConcurrentHashMap<>();

    @Override
    public void save(Token token) {
        tokens.put(token.id(), token);
    }

    @Override
    public void delete(TokenId tokenId) {
        tokens.remove(tokenId);
    }

    @Override
    public Optional<Token> findById(TokenId tokenId) {
        return Optional.ofNullable(tokens.get(tokenId));
    }

    @Override
    public Optional<TokenValue> findTokenValue(TokenId tokenId) {
        return findById(tokenId).map(Token::value);
    }

    @Override
    public boolean existsByName(TokenName name) {
        return tokens.values().stream().anyMatch(token -> token.name().equals(name));
    }

    @Override
    public List<Token> findAll() {
        return List.copyOf(tokens.values());
    }

    public void initialize() {
        List<TokenDto> loaded = fileStorage.load(FILE_NAME, TokenDto.class);
        tokens.putAll(loaded.stream().map(mapper::toDomain).collect(Collectors.toMap(Token::id, Function.identity())));
    }

    public void persist() throws IOException {
        fileStorage.save(FILE_NAME, tokens.values().stream().map(mapper::toDto).toList());
    }
}
