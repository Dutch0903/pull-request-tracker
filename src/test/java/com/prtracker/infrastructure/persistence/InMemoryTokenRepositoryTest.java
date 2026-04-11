package com.prtracker.infrastructure.persistence;

import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.domain.entity.Token;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.domain.valueobject.TokenName;
import com.prtracker.infrastructure.persistence.dto.TokenDto;
import com.prtracker.infrastructure.persistence.mapper.TokenMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.prtracker.testfixtures.domain.entity.TokenTestBuilder.aToken;
import static com.prtracker.testfixtures.infrastructure.persistence.dto.TokenDtoTestBuilder.aTokenDto;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InMemoryTokenRepositoryTest {

    @Mock
    private FileStorage fileStorage;

    @Mock
    private TokenMapper mapper;

    @InjectMocks
    private InMemoryTokenRepository inMemoryTokenRepository;

    @Test
    void save_whenCalled_shouldAddTokenToList() {
        Token token = aToken().build();

        assertEquals(0, inMemoryTokenRepository.findAll().size());
        inMemoryTokenRepository.save(token);

        assertEquals(List.of(token), inMemoryTokenRepository.findAll());
    }

    @Test
    void save_whenTokenAlreadyExists_shouldOverrideExisting() {
        Token token = aToken().build();

        inMemoryTokenRepository.save(token);
        assertEquals(1, inMemoryTokenRepository.findAll().size());

        inMemoryTokenRepository.save(token);
        assertEquals(1, inMemoryTokenRepository.findAll().size());
    }

    @Test
    void delete_whenCalled_shouldDeleteTokenFromList() {
        Token token = aToken().build();
        inMemoryTokenRepository.save(token);

        assertEquals(1, inMemoryTokenRepository.findAll().size());

        inMemoryTokenRepository.delete(token.getId());

        assertEquals(0, inMemoryTokenRepository.findAll().size());
    }

    @Test
    void findById_whenTokenExists_shouldReturnToken() {
        Token token = aToken().build();
        inMemoryTokenRepository.save(token);

        Optional<Token> result = inMemoryTokenRepository.findById(token.getId());

        assertTrue(result.isPresent());
        assertEquals(token, result.get());
    }

    @Test
    void findById_whenTokenDoesNotExist_shouldReturnEmpty() {
        Optional<Token> result = inMemoryTokenRepository.findById(TokenId.create());

        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_whenCalled_shouldReturnAllTokens() {
        Token token1 = aToken().build();
        Token token2 = aToken().build();

        inMemoryTokenRepository.save(token1);
        inMemoryTokenRepository.save(token2);

        List<Token> result = inMemoryTokenRepository.findAll();
        assertTrue(result.contains(token1));
        assertTrue(result.contains(token2));
    }

    @Test
    void existsByName_whenNameExists_shouldReturnTrue() {
        TokenName name = TokenName.from("my-token");
        Token token = aToken().withName(name).build();
        inMemoryTokenRepository.save(token);

        assertTrue(inMemoryTokenRepository.existsByName(name));
    }

    @Test
    void existsByName_whenNameDoesNotExist_shouldReturnFalse() {
        assertFalse(inMemoryTokenRepository.existsByName(TokenName.from("non-existent")));
    }

    @Test
    void findAllAsProjection_whenCalled_shouldReturnProjections() {
        Token token = aToken().build();
        inMemoryTokenRepository.save(token);

        List<TokenProjection> projections = inMemoryTokenRepository.findAllAsProjection();

        assertEquals(1, projections.size());
        assertEquals(token.getId().value(), projections.getFirst().id());
    }

    @Test
    void initialize_whenCalled_shouldLoadFromFileAndMapToToken() {
        TokenDto tokenDto = aTokenDto().build();
        Token token = aToken().build();

        when(fileStorage.load(anyString(), eq(TokenDto.class))).thenReturn(List.of(tokenDto));
        when(mapper.toDomain(tokenDto)).thenReturn(token);

        inMemoryTokenRepository.initialize();

        assertEquals(List.of(token), inMemoryTokenRepository.findAll());

        verify(fileStorage, times(1)).load(anyString(), eq(TokenDto.class));
        verify(mapper, times(1)).toDomain(tokenDto);
    }

    @Test
    void persist_whenCalled_shouldPersistTokensToFile() throws IOException {
        Token token = aToken().build();
        TokenDto tokenDto = aTokenDto().build();
        inMemoryTokenRepository.save(token);

        when(mapper.toDto(token)).thenReturn(tokenDto);

        inMemoryTokenRepository.persist();

        verify(fileStorage, times(1)).save(anyString(), eq(List.of(tokenDto)));
    }
}
