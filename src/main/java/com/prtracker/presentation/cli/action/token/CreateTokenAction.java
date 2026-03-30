package com.prtracker.presentation.cli.action.token;

import com.prtracker.application.command.CreateTokenCommand;
import com.prtracker.application.command.dto.CreateTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTokenAction {
    private final CreateTokenCommand createTokenCommand;

    public void execute(String name, String value) {
        createTokenCommand.execute(new CreateTokenDto(name, value));
    }
}
