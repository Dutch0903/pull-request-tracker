package com.prtracker.presentation.cli.dialog.repository;

import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.presentation.cli.dialog.form.Field;
import com.prtracker.presentation.cli.dialog.form.FormDialogConfiguration;
import com.prtracker.presentation.cli.dialog.form.SelectField;
import com.prtracker.presentation.cli.dialog.form.TextField;

import java.util.ArrayList;
import java.util.List;

public class CreateRepositoryDialogConfiguration implements FormDialogConfiguration {
    public static final String REFERENCE = "reference";
    public static final String TOKEN = "token";

    private final List<String> tokenOptions;

    public CreateRepositoryDialogConfiguration(List<TokenProjection> tokens) {
        List<String> options = new ArrayList<>();
        options.add("None");

        tokens.forEach(token -> options.add(token.name()));
        this.tokenOptions = options;
    }

    @Override
    public List<Field> fields() {
        return List.of(
                new TextField(REFERENCE, "Reference", false, ""),
                new SelectField(TOKEN, "Token", tokenOptions, "None")
        );
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
