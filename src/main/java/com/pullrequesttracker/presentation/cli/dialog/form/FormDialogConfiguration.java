package com.pullrequesttracker.presentation.cli.dialog.form;

import com.pullrequesttracker.presentation.cli.dialog.DialogConfiguration;

import java.util.List;

public interface FormDialogConfiguration extends DialogConfiguration {
    List<Field> fields();
}
