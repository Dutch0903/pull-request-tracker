package com.prtracker.presentation.cli.dialog.token;

import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.presentation.cli.dialog.confirm.ConfirmDialogConfiguration;

public class DeleteTokenDialogConfiguration implements ConfirmDialogConfiguration {
    private final TokenProjection token;

    public DeleteTokenDialogConfiguration(TokenProjection token) {
        this.token = token;
    }

    @Override
    public String title() {
        return "Delete Token";
    }

    @Override
    public String description() {
        return "Are you sure you want to delete the token " + token.name() + "?";
    }
}
