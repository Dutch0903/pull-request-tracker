package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.exception.CreateTokenException;
import com.pullrequesttracker.application.exception.UpdateTokenException;
import com.pullrequesttracker.application.provider.TokenInfo;
import com.pullrequesttracker.application.provider.TokenInfoException;
import com.pullrequesttracker.application.provider.TokenInfoProvider;
import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.repository.TokenPersistenceException;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.valueobject.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static com.pullrequesttracker.testfixtures.domain.model.TokenTestBuilder.aToken;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateTokenTest {
    @Mock
    private TokenRepository tokenRepository;
    @Mock
    private TokenInfoProvider tokenInfoProvider;

    @InjectMocks
    private UpdateToken updateToken;

    private static final String NAME = "name";
    private static final String VALUE = "token";
    private static final TokenId ID = TokenId.create();
    private static final String USERNAME = "username";

    @Test
    void execute_whenCalled_shouldFetchTokenInfoAndSave() throws TokenPersistenceException {
        TokenName tokenName = TokenName.from(NAME);
        TokenValue tokenValue = TokenValue.from(VALUE);
        Token existingToken = aToken().withId(ID).withName(tokenName).build();
        TokenUsername tokenUsername = TokenUsername.from(USERNAME);
        TokenExpirationDate tokenExpirationDate = TokenExpirationDate.from(Instant.now());

        TokenInfo tokenInfo = new TokenInfo(
                tokenUsername,
                tokenExpirationDate
        );

        when(tokenRepository.findById(ID)).thenReturn(Optional.of(existingToken));

        when(tokenInfoProvider.fetchTokenInfo(existingToken.platform(), TokenValue.from(VALUE))).thenReturn(tokenInfo);

        updateToken.execute(ID, tokenName, tokenValue);

        verify(tokenInfoProvider).fetchTokenInfo(existingToken.platform(), TokenValue.from(VALUE));
        verify(tokenRepository).save(argThat(
                token -> token.name().equals(tokenName) && token.value().equals(tokenValue) && token.username().equals(tokenUsername) && token.expirationDate().equals(tokenExpirationDate)
        ));
    }

    @Test
    void execute_whenExistingTokenNotFound_shouldThrowUpdateTokenException() throws TokenPersistenceException {
        TokenName tokenName = TokenName.from(NAME);
        TokenValue tokenValue = TokenValue.from(VALUE);

        when(tokenRepository.findById(ID)).thenReturn(Optional.empty());

        UpdateTokenException exception = assertThrows(UpdateTokenException.class, () -> updateToken.execute(ID, tokenName, tokenValue));

        assertEquals("Token not found: " + ID, exception.getMessage());

        verifyNoInteractions(tokenInfoProvider);
        verify(tokenRepository, never()).save(any());
    }

    @Test
    void execute_whenNameHasNotChanged_shouldNotValidateNameUniqueness() {
        TokenName tokenName = TokenName.from(NAME);
        TokenValue tokenValue = TokenValue.from(VALUE);
        Token token = aToken().withName(tokenName).build();
        TokenUsername tokenUsername = TokenUsername.from(USERNAME);
        TokenExpirationDate tokenExpirationDate = TokenExpirationDate.from(Instant.now());

        TokenInfo tokenInfo = new TokenInfo(
                tokenUsername,
                tokenExpirationDate
        );

        when(tokenRepository.findById(ID)).thenReturn(Optional.of(token));

        when(tokenInfoProvider.fetchTokenInfo(token.platform(), tokenValue)).thenReturn(tokenInfo);

        updateToken.execute(ID, tokenName, tokenValue);

        verify(tokenRepository, never()).existsByName(any());
    }

    @Test
    void execute_whenNameHasChanged_shouldValidateNameUniqueness() {
        TokenName oldTokenName = TokenName.from("random");
        TokenName tokenName = TokenName.from(NAME);
        TokenValue tokenValue = TokenValue.from(VALUE);
        Token token = aToken().withName(oldTokenName).build();

        TokenUsername tokenUsername = TokenUsername.from(USERNAME);
        TokenExpirationDate tokenExpirationDate = TokenExpirationDate.from(Instant.now());

        TokenInfo tokenInfo = new TokenInfo(tokenUsername,  tokenExpirationDate);

        when(tokenRepository.findById(ID)).thenReturn(Optional.of(token));
        when(tokenRepository.existsByName(tokenName)).thenReturn(false);

        when(tokenInfoProvider.fetchTokenInfo(token.platform(), tokenValue)).thenReturn(tokenInfo);

        updateToken.execute(ID, tokenName, tokenValue);

        verify(tokenRepository).existsByName(tokenName);
    }

    @Test
    void execute_whenTokenInfoFailed_shouldThrowUpdateTokenException() throws TokenPersistenceException {
        when(tokenRepository.findById(ID)).thenReturn(Optional.of(aToken().build()));

        doThrow(new TokenInfoException("token info fetch failed")).when(tokenInfoProvider).fetchTokenInfo(any(), any());

        UpdateTokenException ex = assertThrows(UpdateTokenException.class, () -> updateToken.execute(
                ID,
                TokenName.from(NAME),
                TokenValue.from(VALUE)
        ));

        assertEquals("token info fetch failed", ex.getMessage());
        assertInstanceOf(TokenInfoException.class, ex.getCause());
        verify(tokenRepository, never()).save(any());
    }

    @Test
    void execute_whenTokenSaveFailed_shouldThrowUpdateTokenException() throws TokenPersistenceException {
        when(tokenRepository.findById(ID)).thenReturn(Optional.of(aToken().build()));

        when(tokenInfoProvider.fetchTokenInfo(any(), any())).thenReturn(new TokenInfo(
                new TokenUsername(USERNAME),
                new TokenExpirationDate(Instant.now())
        ));

        doThrow(new TokenPersistenceException("token save failed")).when(tokenRepository).save(any());

        UpdateTokenException ex = assertThrows(UpdateTokenException.class, () -> updateToken.execute(
                ID,
                TokenName.from(NAME),
                TokenValue.from(VALUE)
        ));

        assertEquals("token save failed", ex.getMessage());
        assertInstanceOf(TokenPersistenceException.class, ex.getCause());
    }
}
