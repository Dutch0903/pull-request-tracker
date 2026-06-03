package com.pullrequesttracker.presentation.cli.view.token.dialog;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.presentation.cli.dialog.confirm.ConfirmDialogConfiguration;

public class DeleteTokenDialogConfiguration implements ConfirmDialogConfiguration {
    private final TokenDto token;

    public DeleteTokenDialogConfiguration(TokenDto token) {
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
