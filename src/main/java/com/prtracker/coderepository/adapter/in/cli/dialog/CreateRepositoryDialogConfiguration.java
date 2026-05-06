package com.prtracker.coderepository.adapter.in.cli.dialog;

import com.prtracker.shared.cli.dialog.form.Field;
import com.prtracker.shared.cli.dialog.form.FormDialogConfiguration;
import com.prtracker.shared.cli.dialog.form.SelectField;
import com.prtracker.shared.cli.dialog.form.TextField;
import com.prtracker.token.application.query.TokenProjection;

import java.util.ArrayList;
import java.util.List;

public class CreateRepositoryDialogConfiguration implements FormDialogConfiguration {
    private final List<String> tokenOptions;

    public CreateRepositoryDialogConfiguration(List<TokenProjection> tokens) {
        List<String> options = new ArrayList<>();
        options.add("None");

        tokens.forEach(token -> options.add(token.name()));
        this.tokenOptions = options;
    }

    @Override
    public List<Field> fields() {
        return List.of(new TextField(RepositoryFormFields.REFERENCE, "Reference", false, ""),
                new SelectField(RepositoryFormFields.TOKEN, "Token", tokenOptions, "None"));
    }

    @Override
    public String title() {
        return "Register Repository";
    }

    @Override
    public String description() {
        return "Register a repository to track it's pull requests.";
    }
}
