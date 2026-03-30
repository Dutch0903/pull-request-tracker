package com.prtracker.presentation.cli.dialog.token;

import com.prtracker.presentation.cli.dialog.form.Field;
import com.prtracker.presentation.cli.dialog.form.FormDialogConfiguration;
import com.prtracker.presentation.cli.dialog.form.TextField;

import java.util.List;

public class CreateTokenDialogConfiguration implements FormDialogConfiguration {
    public static final String NAME = "name";
    public static final String VALUE = "value";

    @Override
    public List<Field> fields() {
        return List.of(new TextField(NAME, "Name", false, ""), new TextField(VALUE, "Value", true, ""));
    }

    @Override
    public String title() {
        return "Create Token";
    }

    @Override
    public String description() {
        return "Create a new token";
    }
}
