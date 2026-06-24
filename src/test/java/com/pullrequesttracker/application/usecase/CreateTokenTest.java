package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.exception.CreateTokenException;
import com.pullrequesttracker.application.provider.TokenInfo;
import com.pullrequesttracker.application.provider.TokenInfoException;
import com.pullrequesttracker.application.provider.TokenInfoProvider;
import com.pullrequesttracker.domain.repository.TokenPersistenceException;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenExpirationDate;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTokenTest {
    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenInfoProvider tokenInfoProvider;

    @InjectMocks
    private CreateToken createToken;

    private static final String NAME = "name";
    private static final String VALUE = "value";
    private static final String USERNAME = "username";
    private static final Platform PLATFORM = Platform.GITHUB;

    @Test
    void execute_shouldFetchTokenInfoAndSave() throws TokenPersistenceException {
        TokenName tokenName = TokenName.from(NAME);
        TokenValue tokenValue = TokenValue.from(VALUE);

        when(tokenRepository.existsByName(tokenName)).thenReturn(false);

        TokenUsername tokenUsername = new TokenUsername(USERNAME);
        TokenExpirationDate tokenExpirationDate = new TokenExpirationDate(Instant.now());

        TokenInfo tokenInfo = new TokenInfo(tokenUsername, tokenExpirationDate);

        when(tokenInfoProvider.fetch(PLATFORM, tokenValue)).thenReturn(tokenInfo);

        createToken.execute(tokenName, tokenValue, PLATFORM);

        verify(tokenRepository).existsByName(tokenName);
        verify(tokenInfoProvider).fetch(PLATFORM, tokenValue);

        verify(tokenRepository).save(argThat(token -> token.name().equals(tokenName) && token.value().equals(tokenValue)
                && token.platform().equals(PLATFORM) && token.username().equals(tokenUsername)
                && token.expirationDate().equals(tokenExpirationDate)));
    }

    @Test
    void execute_whenTokenNameAlreadyExists_shouldThrowException() throws TokenPersistenceException {
        when(tokenRepository.existsByName(any())).thenReturn(true);

        CreateTokenException ex = assertThrows(CreateTokenException.class,
                () -> createToken.execute(TokenName.from(NAME), TokenValue.from(VALUE), null));

        assertEquals("Token already exists with name: " + NAME, ex.getMessage());
        verify(tokenInfoProvider, never()).fetch(any(), any());
        verify(tokenRepository, never()).save(any());
    }

    @Test
    void execute_whenTokenInfoFailed_shouldThrowException() throws TokenPersistenceException {
        when(tokenRepository.existsByName(any())).thenReturn(false);

        doThrow(new TokenInfoException("token info fetch failed")).when(tokenInfoProvider).fetch(any(), any());

        CreateTokenException ex = assertThrows(CreateTokenException.class,
                () -> createToken.execute(TokenName.from(NAME), TokenValue.from(VALUE), PLATFORM));

        assertEquals("token info fetch failed", ex.getMessage());
        assertInstanceOf(TokenInfoException.class, ex.getCause());
        verify(tokenRepository, never()).save(any());
    }

    @Test
    void execute_whenTokenSaveFailed_shouldThrowException() throws TokenPersistenceException {
        when(tokenRepository.existsByName(any())).thenReturn(false);

        when(tokenInfoProvider.fetch(any(), any()))
                .thenReturn(new TokenInfo(new TokenUsername(USERNAME), new TokenExpirationDate(Instant.now())));

        doThrow(new TokenPersistenceException("token save failed")).when(tokenRepository).save(any());

        CreateTokenException ex = assertThrows(CreateTokenException.class,
                () -> createToken.execute(TokenName.from(NAME), TokenValue.from(VALUE), PLATFORM));

        assertEquals("token save failed", ex.getMessage());
        assertInstanceOf(TokenPersistenceException.class, ex.getCause());
    }
}
