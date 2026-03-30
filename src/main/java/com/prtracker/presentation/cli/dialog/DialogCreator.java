package com.prtracker.presentation.cli.dialog;

public interface DialogCreator<T extends DialogConfiguration> {
    DialogType getDialogType();
    Dialog create(T configuration, DialogHandler handler, Runnable closeDialog);
}
