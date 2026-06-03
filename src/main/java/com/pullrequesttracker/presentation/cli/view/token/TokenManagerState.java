package com.pullrequesttracker.presentation.cli.view.token;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.application.usecase.FetchAllTokens;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenManagerState {
    private final FetchAllTokens getTokens;

    @Getter
    private List<TokenDto> tokens;

    @Getter
    private int selectedIndex = 0;

    @PostConstruct
    public void init() {
        tokens = getTokens.execute();
    }

    public void refreshTokens() {
        tokens = getTokens.execute();
        selectedIndex = Math.clamp(selectedIndex, 0, tokens.isEmpty() ? 0 : tokens.size() - 1);
    }

    public TokenDto getSelectedToken() {
        if (tokens.isEmpty()) return null;
        return tokens.get(selectedIndex);
    }

    public void selectPrevious() {
        if (selectedIndex > 0) selectedIndex--;
    }

    public void selectNext() {
        if (selectedIndex < tokens.size() - 1) selectedIndex++;
    }
}
