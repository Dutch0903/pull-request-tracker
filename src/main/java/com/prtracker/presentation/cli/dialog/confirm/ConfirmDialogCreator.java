package com.prtracker.presentation.cli.dialog.confirm;

import com.prtracker.presentation.cli.dialog.Dialog;
import com.prtracker.presentation.cli.dialog.DialogCreator;
import com.prtracker.presentation.cli.dialog.DialogHandler;
import com.prtracker.presentation.cli.dialog.DialogType;
import org.springframework.stereotype.Component;

@Component
public class ConfirmDialogCreator implements DialogCreator<ConfirmDialogConfiguration> {
    @Override
    public DialogType getDialogType() {
        return DialogType.CONFIRM;
    }

    @Override
    public Dialog create(ConfirmDialogConfiguration configuration, DialogHandler handler, Runnable closeDialog) {
        ConfirmDialogHandler confirmDialogHandler = (ConfirmDialogHandler) handler;
        return new ConfirmDialog(configuration, confirmDialogHandler, closeDialog);
    }
}
