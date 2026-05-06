package com.prtracker.shared.cli.dialog;

import org.springframework.stereotype.Component;

@Component
public class DialogManager {
    private final DialogFactory dialogFactory;

    private Dialog currentDialog;

    public DialogManager(DialogFactory dialogFactory) {
        this.dialogFactory = dialogFactory;
    }

    public void openDialog(DialogType type, DialogConfiguration configuration, DialogHandler handler) {
        currentDialog = dialogFactory.create(type, configuration, handler, this::dismissDialog);
    }

    public void dismissDialog() {
        currentDialog = null;
    }

    public boolean isDialogOpen() {
        return currentDialog != null;
    }

    public Dialog getCurrentDialog() {
        return currentDialog;
    }
}
