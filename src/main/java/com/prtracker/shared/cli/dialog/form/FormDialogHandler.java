package com.prtracker.shared.cli.dialog.form;

import com.prtracker.shared.cli.dialog.DialogHandler;

import java.util.Map;

public interface FormDialogHandler extends DialogHandler {
    void onSubmit(Map<String, String> values);
}
