package com.pullrequesttracker.presentation.cli.dialog.confirm;

import com.pullrequesttracker.presentation.cli.dialog.Dialog;
import com.pullrequesttracker.presentation.cli.dialog.DialogCreator;
import com.pullrequesttracker.presentation.cli.dialog.DialogHandler;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import org.springframework.stereotype.Component;

@Component
public class ConfirmDialogCreator implements DialogCreator<ConfirmDialogConfiguration> {
    @Override
    public DialogType getDialogType() {
        return DialogType.CONFIRM;
    }

    @Override
    public Dialog create(ConfirmDialogConfiguration configuration, DialogHandler handler, Runnable closeDialog) {
        return new ConfirmDialog(configuration, (ConfirmDialogHandler) handler, closeDialog);
    }
}
