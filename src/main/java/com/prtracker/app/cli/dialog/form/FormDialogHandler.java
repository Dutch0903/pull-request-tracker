package com.prtracker.app.cli.dialog.form;

import com.prtracker.app.cli.dialog.DialogHandler;

import java.util.Map;

public interface FormDialogHandler extends DialogHandler {
    void onSubmit(Map<String, String> values);
}
