package com.prtracker.presentation.cli.dialog.token;

import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.presentation.cli.dialog.form.Field;
import com.prtracker.presentation.cli.dialog.form.FormDialogConfiguration;
import com.prtracker.presentation.cli.dialog.form.TextField;

import java.util.List;

public class UpdateTokenDialogConfiguration implements FormDialogConfiguration {
    private final TokenProjection token;

    public UpdateTokenDialogConfiguration(TokenProjection token) {
        this.token = token;
    }

    @Override
    public List<Field> fields() {
        return List.of(new TextField(TokenFormFields.NAME, "Name", false, token.name()),
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
