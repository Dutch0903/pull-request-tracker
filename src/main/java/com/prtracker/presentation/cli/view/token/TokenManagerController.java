package com.prtracker.presentation.cli.view.token;

import com.prtracker.application.command.AddTokenCommand;
import com.prtracker.application.dto.AddTokenDto;
import dev.tamboui.widgets.input.TextInputState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenManagerController {
    private final AddTokenCommand addTokenCommand;

    private DialogType currentDialog = DialogType.NONE;
    private String dialogMessage = "";
    private final TextInputState nameInputState = new TextInputState();
    private final TextInputState tokenInputState = new TextInputState();

    public DialogType getCurrentDialog() {
        return currentDialog;
    }

    public String getDialogMessage() {
        return dialogMessage;
    }

    public TextInputState getNameInputState() {
        return nameInputState;
    }

    public TextInputState getTokenInputState() {
        return tokenInputState;
    }

    public boolean hasDialog() {
        return currentDialog != DialogType.NONE;
    }

    public void promptCreateToken() {
        this.resetInputStates();
        dialogMessage = "Create a new token";
        currentDialog = DialogType.CREATE;
    }

    public void promptUpdateToken() {
        this.resetInputStates();
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
        this.resetInputStates();
    }

    private void resetInputStates() {
        nameInputState.clear();
        tokenInputState.clear();
    }

    private void createToken() {
        String name = nameInputState.text();
        String token = tokenInputState.text();

        addTokenCommand.execute(new AddTokenDto(name, token));
    }

    private void updateToken() {

    }
}
