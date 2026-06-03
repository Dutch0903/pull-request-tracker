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
public class UpdateToken {
    private final TokenDomainService tokenDomainService;

    public void execute(TokenId id, TokenName name, TokenValue value) {
        tokenDomainService.update(new Token(id, name, value));
    }
}
