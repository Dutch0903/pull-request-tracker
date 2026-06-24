package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.exception.DeleteTokenException;
import com.pullrequesttracker.domain.repository.TokenPersistenceException;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.valueobject.TokenId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteTokenTest {
    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private DeleteToken deleteToken;

    @Test
    void execute_shouldDeleteToken() throws TokenPersistenceException {
        TokenId tokenId = TokenId.create();

        deleteToken.execute(tokenId);

        verify(tokenRepository).delete(tokenId);
    }

    @Test
    void execute_whenTokenPersistenceExceptionIsThrown_shouldThrowException() throws TokenPersistenceException {
        TokenId tokenId = TokenId.create();
        TokenPersistenceException toBeThrownException = new TokenPersistenceException("Failed to delete token");

        doThrow(toBeThrownException).when(tokenRepository).delete(tokenId);

        DeleteTokenException thrownException = assertThrows(DeleteTokenException.class,
                () -> deleteToken.execute(tokenId));

        assertEquals(thrownException.getMessage(), toBeThrownException.getMessage());
        assertEquals(thrownException.getCause(), toBeThrownException);
    }
}
