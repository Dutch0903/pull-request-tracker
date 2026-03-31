package com.prtracker.presentation.cli.view.token;

import com.prtracker.application.query.GetTokensQuery;
import com.prtracker.application.query.dto.TokenProjection;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenManagerState {
    private final GetTokensQuery getTokensQuery;

    @Getter
    private List<TokenProjection> tokens;

    @Getter
    private int selectedIndex = 0;

    @PostConstruct
    public void init() {
        tokens = getTokensQuery.execute();
    }

    public void refreshTokens() {
        tokens = getTokensQuery.execute();
        selectedIndex = Math.clamp(selectedIndex, 0, tokens.size() - 1);
    }

    public TokenProjection getSelectedToken() {
        if (tokens.isEmpty()) {
            return null;
        }

        return tokens.get(selectedIndex);
    }

    public void selectPrevious() {
        if (selectedIndex > 0) {
            selectedIndex--;
        }
    }

    public void selectNext() {
        if (selectedIndex < tokens.size() - 1) {
            selectedIndex++;
        }
    }
}
