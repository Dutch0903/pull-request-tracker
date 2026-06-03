package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.model.Token;
import com.pullrequesttracker.domain.service.TokenDomainService;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateToken {
    private final TokenDomainService tokenDomainService;

    public void execute(TokenName name, TokenValue value) {
        tokenDomainService.create(new Token(TokenId.create(), name, value));
    }
}
