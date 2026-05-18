package com.prtracker.app.cli.token;

import com.prtracker.app.cli.dialog.confirm.ConfirmDialogConfiguration;
import com.prtracker.token.application.query.TokenProjection;

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
