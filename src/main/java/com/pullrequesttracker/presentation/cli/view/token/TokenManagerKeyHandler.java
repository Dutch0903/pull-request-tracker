package com.pullrequesttracker.presentation.cli.view.token;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.view.token.action.NavigateToDashboard;
import com.pullrequesttracker.presentation.cli.view.token.action.OpenCreateTokenDialog;
import com.pullrequesttracker.presentation.cli.view.token.action.OpenDeleteTokenDialog;
import com.pullrequesttracker.presentation.cli.view.token.action.OpenUpdateTokenDialog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenManagerKeyHandler extends KeyHandler {
    public TokenManagerKeyHandler(NavigateToDashboard navigateToDashboard,
            OpenCreateTokenDialog openCreateTokenDialog, OpenUpdateTokenDialog openUpdateTokenDialog,
            OpenDeleteTokenDialog openDeleteTokenDialog) {
        super(List.of(navigateToDashboard, openCreateTokenDialog, openUpdateTokenDialog, openDeleteTokenDialog));
    }
}
