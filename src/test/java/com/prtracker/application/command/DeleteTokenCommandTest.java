package com.prtracker.application.command;

import com.prtracker.application.command.dto.DeleteTokenDto;
import com.prtracker.domain.repository.TokenRepository;
import com.prtracker.domain.service.TokenService;
import com.prtracker.domain.valueobject.TokenId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteTokenCommandTest {
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private DeleteTokenCommand deleteTokenCommand;

    @Test
    void execute_whenCalled_shouldCallTokenService() {
        DeleteTokenDto dto = new DeleteTokenDto(UUID.randomUUID());

        deleteTokenCommand.execute(dto);

        verify(tokenService, times(1)).delete(TokenId.from(dto.id()));
    }
}
