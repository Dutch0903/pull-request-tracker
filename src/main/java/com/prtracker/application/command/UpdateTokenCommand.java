package com.prtracker.application.command;

import com.prtracker.application.command.dto.UpdateTokenDto;
import com.prtracker.domain.entity.Token;
import com.prtracker.domain.service.TokenService;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.domain.valueobject.TokenName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateTokenCommand extends Command<UpdateTokenDto, Void> {
    private final TokenService tokenService;

    protected Void executeInternal(UpdateTokenDto tokenDto) {
        Token token = new Token(TokenId.from(tokenDto.id()), TokenName.from(tokenDto.name()), tokenDto.value());

        tokenService.update(token);

        return null;
    }
}
