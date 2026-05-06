package com.prtracker.token.application.command;

import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.domain.model.Token;
import com.prtracker.token.domain.model.TokenName;
import com.prtracker.token.domain.service.TokenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateToken {
    private final TokenDomainService tokenDomainService;

    public void execute(CreateTokenDto dto) {
        Token token = new Token(TokenId.create(), TokenName.from(dto.name()), dto.value());
        tokenDomainService.create(token);
    }
}
