package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.domain.service.TokenDomainService;
import com.pullrequesttracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteToken {
    private final TokenDomainService tokenDomainService;

    public void execute(TokenId id) {
        tokenDomainService.delete(id);
    }
}
