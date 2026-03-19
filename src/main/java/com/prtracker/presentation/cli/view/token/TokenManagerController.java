package com.prtracker.presentation.cli.view.token;

import com.prtracker.application.command.AddTokenCommand;
import com.prtracker.application.command.UpdateTokenCommand;
import com.prtracker.application.dto.AddTokenDto;
import com.prtracker.application.dto.TokenProjection;
import com.prtracker.application.dto.UpdateTokenDto;
import com.prtracker.application.query.GetTokensQuery;
import dev.tamboui.widgets.input.TextInputState;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenManagerController {
    private final AddTokenCommand addTokenCommand;
    private final UpdateTokenCommand updateTokenCommand;
    private final GetTokensQuery getTokensQuery;

    // Dialog
    @Getter
    private DialogType currentDialog = DialogType.NONE;
    @Getter
    private String dialogMessage = "";

    // Form
    @Getter
    private final TextInputState nameInputState = new TextInputState();
    @Getter
    private final TextInputState valueInputState = new TextInputState();

    // List
    private List<TokenProjection> tokens;

    @Getter
    private int selectedIndex = 0;

    public List<TokenProjection> getTokens() {
        if (tokens == null) {
            loadTokens();
        }

        return tokens;
    }

    public void refreshTokens() {
        loadTokens();
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

    public boolean hasDialog() {
        return currentDialog != DialogType.NONE;
    }

    public void promptCreateToken() {
        this.clearInputStates();
        dialogMessage = "Create a new token";
        currentDialog = DialogType.CREATE;
    }

    public void promptUpdateToken() {
        this.prefillInputStates(getSelectedToken());
        dialogMessage = "Update a existing token";
        currentDialog = DialogType.UPDATE;
    }

    public void confirmDialog() {
        switch (currentDialog) {
            case CREATE -> this.createToken();
            case UPDATE -> this.updateToken();
        }

        dismissDialog();
    }

    public void dismissDialog() {
        currentDialog = DialogType.NONE;
        this.clearInputStates();
    }

    private void clearInputStates() {
        nameInputState.clear();
        valueInputState.clear();
    }

    private void prefillInputStates(TokenProjection token) {
        if (token != null) {
            nameInputState.setText(token.name());
            valueInputState.setText(token.value());
        } else {
            clearInputStates();
        }
    }

    private void createToken() {
        String name = nameInputState.text();
        String value = valueInputState.text();

        addTokenCommand.execute(new AddTokenDto(name, value));

        refreshTokens();
    }

    private void updateToken() {
        TokenProjection token = getSelectedToken();
        String name = nameInputState.text();
        String value = valueInputState.text();

        updateTokenCommand.execute(new UpdateTokenDto(token.id(), name, value));

        refreshTokens();;
    }

    private void loadTokens() {
        tokens = getTokensQuery.execute();
    }
}
