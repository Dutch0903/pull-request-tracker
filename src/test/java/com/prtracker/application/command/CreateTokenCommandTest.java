package com.prtracker.application.command;

import com.prtracker.application.command.dto.CreateTokenDto;
import com.prtracker.domain.entity.Token;
import com.prtracker.domain.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreateTokenCommandTest {
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private CreateTokenCommand createTokenCommand;

    @Test
    void execute_whenCalled_shouldCallTokenService() {
        String value = "value";
        String name = "name";
        CreateTokenDto input = new CreateTokenDto(name, value);

        createTokenCommand.execute(input);

        ArgumentCaptor<Token> argument = ArgumentCaptor.forClass(Token.class);
        verify(tokenService, times(1)).create(argument.capture());

        Token capturedToken = argument.getValue();
        assertEquals(name, capturedToken.getName().value());
        assertEquals(value, capturedToken.getValue());
    }
}
