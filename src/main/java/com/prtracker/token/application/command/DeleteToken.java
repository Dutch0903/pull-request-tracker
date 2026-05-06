package com.prtracker.token.application.command;

import com.prtracker.shared.kernel.TokenId;
import com.prtracker.token.domain.service.TokenDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteToken {
    private final TokenDomainService tokenDomainService;

    public void execute(DeleteTokenDto dto) {
        tokenDomainService.delete(TokenId.from(dto.id()));
    }
}
