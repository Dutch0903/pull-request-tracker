package com.prtracker.app.cli.dialog.confirm;

import com.prtracker.app.cli.dialog.Dialog;
import com.prtracker.app.cli.dialog.DialogCreator;
import com.prtracker.app.cli.dialog.DialogHandler;
import com.prtracker.app.cli.dialog.DialogType;
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
