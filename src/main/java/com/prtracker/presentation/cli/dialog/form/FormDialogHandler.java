package com.prtracker.presentation.cli.dialog.form;

import com.prtracker.presentation.cli.dialog.DialogHandler;

import java.util.Map;

public interface FormDialogHandler extends DialogHandler {
    void onSubmit(Map<String, String> values);
}
