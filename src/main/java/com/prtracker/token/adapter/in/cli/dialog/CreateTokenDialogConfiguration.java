package com.prtracker.token.adapter.in.cli.dialog;

import com.prtracker.shared.cli.dialog.form.Field;
import com.prtracker.shared.cli.dialog.form.FormDialogConfiguration;
import com.prtracker.shared.cli.dialog.form.TextField;

import java.util.List;

public class CreateTokenDialogConfiguration implements FormDialogConfiguration {
    @Override
    public List<Field> fields() {
        return List.of(new TextField(TokenFormFields.NAME, "Name", false, ""),
                new TextField(TokenFormFields.VALUE, "Value", true, ""));
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
