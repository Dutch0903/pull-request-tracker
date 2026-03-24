package com.prtracker.application.command;

import com.prtracker.application.command.dto.AddTokenDto;
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
public class AddTokenCommandTest {
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AddTokenCommand addTokenCommand;

    @Test
    void execute_whenCalled_shouldCallTokenService() {
        String value = "value";
        String name = "name";
        AddTokenDto input = new AddTokenDto(name, value);

        addTokenCommand.execute(input);

        ArgumentCaptor<Token> argument = ArgumentCaptor.forClass(Token.class);
        verify(tokenService, times(1)).add(argument.capture());

        Token capturedToken = argument.getValue();
        assertEquals(name, capturedToken.getName().value());
        assertEquals(value, capturedToken.getValue());
    }
}
