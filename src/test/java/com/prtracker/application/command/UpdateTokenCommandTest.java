package com.prtracker.application.command;

import com.prtracker.application.command.dto.UpdateTokenDto;
import com.prtracker.domain.entity.Token;
import com.prtracker.domain.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UpdateTokenCommandTest {
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private UpdateTokenCommand updateTokenCommand;

    @Test
    void execute_whenCalled_shouldCallTokenService() {
        UUID tokenId = UUID.randomUUID();
        String name = "name";
        String value = "value";

        UpdateTokenDto input = new UpdateTokenDto(tokenId, name, value);

        updateTokenCommand.execute(input);

        ArgumentCaptor<Token> argument = ArgumentCaptor.forClass(Token.class);
        verify(tokenService, times(1)).update(argument.capture());

        Token capturedToken = argument.getValue();
        assertEquals(tokenId, capturedToken.getId().value());
        assertEquals(name, capturedToken.getName().value());
        assertEquals(value, capturedToken.getValue());
    }
}
