package com.prtracker.presentation.cli.dialog.confirm;

import com.prtracker.presentation.cli.dialog.Dialog;
import dev.tamboui.toolkit.elements.DialogElement;

import static dev.tamboui.toolkit.Toolkit.dialog;
import static dev.tamboui.toolkit.Toolkit.text;

public class ConfirmDialog implements Dialog {
    private final ConfirmDialogConfiguration configuration;
    private final ConfirmDialogHandler handler;
    private final Runnable closeDialog;

    private String errorMessage;

    public ConfirmDialog(ConfirmDialogConfiguration configuration, ConfirmDialogHandler handler, Runnable closeDialog) {
        this.configuration = configuration;
        this.handler = handler;
        this.closeDialog = closeDialog;
    }

    @Override
    public DialogElement render() {
        return dialog(configuration.title(), text(configuration.description()), text(errorMessage).red(),
                text("[Enter] Confirm  [Esc] Cancel").dim()).onConfirm(this::confirm).onCancel(closeDialog)
                .width(Math.max(50, configuration.description().length()));
    }

    public void confirm() {
        try {
            handler.onConfirm();
        } catch (RuntimeException e) {
            this.errorMessage = e.getMessage();
            return;
        }
        closeDialog.run();
    }
}
