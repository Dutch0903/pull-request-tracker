package com.prtracker.app.cli.dialog.form;

import com.prtracker.app.cli.dialog.DialogConfiguration;

import java.util.List;

public interface FormDialogConfiguration extends DialogConfiguration {
    List<Field> fields();
}
