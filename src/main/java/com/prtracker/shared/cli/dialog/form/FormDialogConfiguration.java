package com.prtracker.shared.cli.dialog.form;

import com.prtracker.shared.cli.dialog.DialogConfiguration;

import java.util.List;

public interface FormDialogConfiguration extends DialogConfiguration {
    List<Field> fields();
}
