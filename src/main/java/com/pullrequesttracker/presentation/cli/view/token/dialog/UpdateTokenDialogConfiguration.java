package com.pullrequesttracker.presentation.cli.view.token.dialog;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.presentation.cli.dialog.form.Field;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogConfiguration;
import com.pullrequesttracker.presentation.cli.dialog.form.TextField;
import com.pullrequesttracker.presentation.cli.view.token.TokenFormFields;

import java.util.List;

public class UpdateTokenDialogConfiguration implements FormDialogConfiguration {
    private final TokenDto token;

    public UpdateTokenDialogConfiguration(TokenDto token) {
        this.token = token;
    }

    @Override
    public List<Field> fields() {
        return List.of(
                new TextField(TokenFormFields.NAME, "Name", false, token.name()),
                new TextField(TokenFormFields.VALUE, "Value", true, token.value()));
    }

    @Override
    public String title() {
        return "Update token";
    }

    @Override
    public String description() {
        return "Update existing token";
    }
}
