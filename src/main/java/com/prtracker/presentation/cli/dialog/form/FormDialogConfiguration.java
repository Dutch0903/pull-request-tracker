package com.prtracker.presentation.cli.dialog.form;

import com.prtracker.presentation.cli.dialog.DialogConfiguration;

import java.util.List;

public interface FormDialogConfiguration extends DialogConfiguration {
    List<Field> fields();
}
