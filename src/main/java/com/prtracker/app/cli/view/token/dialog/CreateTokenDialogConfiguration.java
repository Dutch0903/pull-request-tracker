package com.prtracker.app.cli.view.token.dialog;

import com.prtracker.app.cli.dialog.form.Field;
import com.prtracker.app.cli.dialog.form.FormDialogConfiguration;
import com.prtracker.app.cli.dialog.form.TextField;
import com.prtracker.app.cli.view.token.TokenFormFields;

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
