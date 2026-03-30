package com.prtracker.presentation.cli.action.token;

import com.prtracker.application.command.UpdateTokenCommand;
import com.prtracker.application.command.dto.UpdateTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UpdateTokenAction {
    private final UpdateTokenCommand updateTokenCommand;

    public void execute(UUID tokenId, String name, String value) {
        updateTokenCommand.execute(new UpdateTokenDto(tokenId, name, value));
    }
}
