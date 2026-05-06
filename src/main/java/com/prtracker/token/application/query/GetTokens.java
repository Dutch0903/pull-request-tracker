package com.prtracker.token.application.query;

import com.prtracker.token.application.query.port.TokenReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetTokens {
    private final TokenReadPort tokenReadPort;

    public List<TokenProjection> execute() {
        return tokenReadPort.findAllAsProjection();
    }
}
