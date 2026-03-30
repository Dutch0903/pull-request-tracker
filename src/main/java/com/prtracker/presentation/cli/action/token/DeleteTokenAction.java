package com.prtracker.presentation.cli.action.token;

import com.prtracker.application.command.DeleteTokenCommand;
import com.prtracker.application.command.dto.DeleteTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteTokenAction {
    private final DeleteTokenCommand command;

    public void execute(UUID tokenId) {
        command.execute(new DeleteTokenDto(tokenId));
    }
}
