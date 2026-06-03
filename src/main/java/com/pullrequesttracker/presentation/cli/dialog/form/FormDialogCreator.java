package com.pullrequesttracker.presentation.cli.dialog.form;

import com.pullrequesttracker.presentation.cli.dialog.Dialog;
import com.pullrequesttracker.presentation.cli.dialog.DialogCreator;
import com.pullrequesttracker.presentation.cli.dialog.DialogHandler;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import org.springframework.stereotype.Component;

@Component
public class FormDialogCreator implements DialogCreator<FormDialogConfiguration> {
    @Override
    public DialogType getDialogType() {
        return DialogType.FORM;
    }

    @Override
    public Dialog create(FormDialogConfiguration configuration, DialogHandler handler, Runnable closeDialog) {
        return new FormDialog(configuration, (FormDialogHandler) handler, closeDialog);
    }
}
