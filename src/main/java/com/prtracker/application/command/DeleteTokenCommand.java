package com.prtracker.application.command;

import com.prtracker.application.command.dto.DeleteTokenDto;
import com.prtracker.domain.service.TokenService;
import com.prtracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteTokenCommand extends Command<DeleteTokenDto, Void> {
    private final TokenService tokenService;

    @Override
    protected Void executeInternal(DeleteTokenDto input) {
        tokenService.delete(TokenId.from(input.id()));
        return null;
    }
}
