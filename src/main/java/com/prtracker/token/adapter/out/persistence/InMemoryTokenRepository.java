package com.prtracker.token.adapter.out.persistence;

import com.prtracker.shared.persistence.FileStorage;
import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.application.query.TokenProjection;
import com.prtracker.token.application.query.port.TokenReadPort;
import com.prtracker.token.domain.model.Token;
import com.prtracker.token.domain.model.TokenName;
import com.prtracker.token.domain.port.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryTokenRepository implements TokenRepository, TokenReadPort {
    private static final String FILE_NAME = "tokens.json";
    private final FileStorage fileStorage;
    private final TokenMapper mapper;

    private final ConcurrentHashMap<TokenId, Token> tokens = new ConcurrentHashMap<>();

    @Override
    public void save(Token token) {
        tokens.put(token.getId(), token);
    }

    @Override
    public void delete(TokenId tokenId) {
        tokens.remove(tokenId);
    }

    @Override
    public int count() {
        return tokens.size();
    }

    @Override
    public Optional<Token> findById(TokenId id) {
        return tokens.values().stream().filter(token -> token.getId().equals(id)).findFirst();
    }

    @Override
    public List<Token> findAll() {
        return List.copyOf(tokens.values());
    }

    @Override
    public boolean existsByName(TokenName name) {
        return tokens.values().stream().anyMatch(token -> token.getName().equals(name));
    }

    @Override
    public List<TokenProjection> findAllAsProjection() {
        return tokens.values().stream().map(TokenProjection::from).toList();
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
