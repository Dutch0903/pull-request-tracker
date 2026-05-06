package com.prtracker.token.application.command;

import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.domain.model.Token;
import com.prtracker.token.domain.model.TokenName;
import com.prtracker.token.domain.service.TokenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateToken {
    private final TokenDomainService tokenDomainService;

    public void execute(UpdateTokenDto dto) {
        Token token = new Token(TokenId.from(dto.id()), TokenName.from(dto.name()), dto.value());
        tokenDomainService.update(token);
    }
}
