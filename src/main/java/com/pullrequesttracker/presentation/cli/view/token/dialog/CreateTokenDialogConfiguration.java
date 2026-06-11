package com.pullrequesttracker.presentation.cli.view.token.dialog;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.presentation.cli.dialog.form.Field;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogConfiguration;
import com.pullrequesttracker.presentation.cli.dialog.form.SelectField;
import com.pullrequesttracker.presentation.cli.dialog.form.TextField;
import com.pullrequesttracker.presentation.cli.view.token.TokenFormFields;

import java.util.Arrays;
import java.util.List;

public class CreateTokenDialogConfiguration implements FormDialogConfiguration {

    private static final List<String> PLATFORM_OPTIONS = Arrays.stream(Platform.values()).map(Platform::name).toList();

    @Override
    public List<Field> fields() {
        return List.of(new TextField(TokenFormFields.NAME, "Name", false, ""),
                new SelectField(TokenFormFields.PLATFORM, "Platform", PLATFORM_OPTIONS, Platform.GITHUB.name()),
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
