package com.prtracker.domain.service;

import com.prtracker.domain.entity.Token;
import com.prtracker.domain.exceptions.TokenAlreadyExistsException;
import com.prtracker.domain.exceptions.TokenNotFoundException;
import com.prtracker.domain.repository.TokenRepository;
import com.prtracker.domain.valueobject.TokenName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.prtracker.testfixtures.TokenTestBuilder.aToken;
import static com.prtracker.testfixtures.TokenTestBuilder.copyOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TokenServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenService tokenService;

    @Test
    void add_whenTokenNameDoesNotExists_shouldSaveToken() {
        Token newToken = aToken()
                .withName(TokenName.from("Unique name"))
                .build();

        when(tokenRepository.existsByName(newToken.getName())).thenReturn(false);

        tokenService.add(newToken);

        verify(tokenRepository, times(1)).existsByName(newToken.getName());
        verify(tokenRepository, times(1)).save(newToken);
    }

    @Test
    void add_whenTokenNameAlreadyExists_shouldThrowTokenAlreadyExistsException() {
        Token newToken = aToken()
                .withName(TokenName.from("Already existing name"))
                .build();


        when(tokenRepository.existsByName(newToken.getName())).thenReturn(true);

        TokenAlreadyExistsException exception = assertThrows(
                TokenAlreadyExistsException.class,
                () -> tokenService.add(newToken)
        );

        verify(tokenRepository, times(0)).save(newToken);

        assertEquals("Token with name " + newToken.getName().value() + " already exists", exception.getMessage());
    }

    @Test
    void update_whenTokenDoesNotExists_shouldThrowTokenNotFoundException() {
        Token updatedToken = aToken().build();

        when(tokenRepository.findById(updatedToken.getId())).thenReturn(Optional.empty());

        TokenNotFoundException exception = assertThrows(
                TokenNotFoundException.class,
                () -> tokenService.update(updatedToken)
        );

        verify(tokenRepository, times(1)).findById(updatedToken.getId());

        assertEquals("Token not found: " + updatedToken.getId().value(), exception.getMessage());
    }

    @Test
    void update_whenTokenNameHasChangedToExistingName_shouldThrowTokenAlreadyExistsException() {
        Token existingToken = aToken()
                .withName(TokenName.from("existing-name"))
                .build();

        Token updatedToken = copyOf(existingToken)
                .withName(TokenName.from("new-name"))
                .build();

        when(tokenRepository.findById(updatedToken.getId())).thenReturn(Optional.of(existingToken));
        when(tokenRepository.existsByName(updatedToken.getName())).thenReturn(true);

        assertThrows(TokenAlreadyExistsException.class,
                () -> tokenService.update(updatedToken));
    }

    @Test
    void update_whenTokenNameHasChangedToAUniqueName_shouldSaveToken() {
        Token existingToken = aToken()
                .withName(TokenName.from("existing-name"))
                .build();

        Token updatedToken = copyOf(existingToken)
                .withName(TokenName.from("unique-name"))
                .build();

        when(tokenRepository.findById(updatedToken.getId())).thenReturn(Optional.of(existingToken));
        when(tokenRepository.existsByName(updatedToken.getName())).thenReturn(false);

        tokenService.update(updatedToken);

        verify(tokenRepository, times(1)).save(updatedToken);
    }

    @Test
    void update_whenTokenNameIsStillTheSame_shouldNotCheckForExistingTokens() {
        Token existingToken = aToken()
                .withName(TokenName.from("existing-name"))
                .build();

        Token updatedToken = copyOf(existingToken)
                .withValue("New value")
                .build();

        when(tokenRepository.findById(updatedToken.getId())).thenReturn(Optional.of(existingToken));

        tokenService.update(updatedToken);

        verify(tokenRepository, times(0)).existsByName(updatedToken.getName());
        verify(tokenRepository, times(1)).save(updatedToken);
    }
}
