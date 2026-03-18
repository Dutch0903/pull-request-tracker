package com.prtracker.presentation.cli.view.token;

import dev.tamboui.widgets.input.TextInputState;
import org.springframework.stereotype.Component;

@Component
public class TokenManagerController {
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
}
