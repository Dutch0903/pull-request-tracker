package com.pullrequesttracker.presentation.cli.view.token.dialog;

import com.pullrequesttracker.presentation.cli.dialog.form.Field;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogConfiguration;
import com.pullrequesttracker.presentation.cli.dialog.form.TextField;
import com.pullrequesttracker.presentation.cli.view.token.TokenFormFields;

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
