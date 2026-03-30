package com.prtracker.presentation.cli.dialog.form;

import com.prtracker.presentation.cli.dialog.Dialog;
import com.prtracker.presentation.cli.dialog.DialogCreator;
import com.prtracker.presentation.cli.dialog.DialogHandler;
import com.prtracker.presentation.cli.dialog.DialogType;
import org.springframework.stereotype.Component;

@Component
public class FormDialogCreator implements DialogCreator<FormDialogConfiguration> {

    @Override
    public DialogType getDialogType() {
        return DialogType.FORM;
    }

    @Override
    public Dialog create(FormDialogConfiguration configuration, DialogHandler handler, Runnable closeDialog) {
        FormDialogHandler formDialogHandler = (FormDialogHandler) handler;

        return new FormDialog(configuration, formDialogHandler, closeDialog);
    }
}
