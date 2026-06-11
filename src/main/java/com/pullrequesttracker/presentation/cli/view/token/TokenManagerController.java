package com.pullrequesttracker.presentation.cli.view.token;

import com.pullrequesttracker.application.usecase.FetchAllTokens;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenManagerController {
    private final TokenManagerState state;
    private final FetchAllTokens fetchAllTokens;

    public void loadTokens() {
        state.set(TokenManagerState.TOKENS, fetchAllTokens.execute());
    }
}
