package com.prtracker.application.command;

import com.prtracker.application.command.dto.AddTokenDto;
import com.prtracker.domain.entity.Token;
import com.prtracker.domain.service.TokenService;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.domain.valueobject.TokenName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddTokenCommand extends Command<AddTokenDto, Void> {
    private final TokenService tokenService;

    @Override
    protected Void executeInternal(AddTokenDto input) {
        Token token = new Token(TokenId.create(), TokenName.from(input.name()), input.value());

        tokenService.add(token);
        return null;
    }
}
