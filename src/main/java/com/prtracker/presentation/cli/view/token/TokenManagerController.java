package com.prtracker.presentation.cli.view.token;

import com.prtracker.application.query.GetTokensQuery;
import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.presentation.cli.action.token.CreateTokenAction;
import com.prtracker.presentation.cli.action.token.DeleteTokenAction;
import com.prtracker.presentation.cli.action.token.UpdateTokenAction;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.dialog.DialogType;
import com.prtracker.presentation.cli.dialog.confirm.ConfirmDialogHandler;
import com.prtracker.presentation.cli.dialog.form.FormDialogHandler;
import com.prtracker.presentation.cli.dialog.token.CreateTokenDialogConfiguration;
import com.prtracker.presentation.cli.dialog.token.DeleteTokenDialogConfiguration;
import com.prtracker.presentation.cli.dialog.token.UpdateTokenDialogConfiguration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenManagerController {
    private final DialogManager dialogManager;
    private final CreateTokenAction createTokenAction;
    private final UpdateTokenAction updateTokenAction;
    private final DeleteTokenAction deleteTokenAction;
    private final GetTokensQuery getTokensQuery;

    private List<TokenProjection> tokens;

    @Getter
    private int selectedIndex = 0;

    public List<TokenProjection> getTokens() {
        if (tokens == null) {
            loadTokens();
        }

        return tokens;
    }

    public TokenProjection getSelectedToken() {
        if (tokens.isEmpty()) {
            return null;
        }

        return tokens.get(selectedIndex);
    }

    public void selectPrevious() {
        if (selectedIndex > 0) {
            selectedIndex--;
        }
    }

    public void selectNext() {
        if (selectedIndex < tokens.size() - 1) {
            selectedIndex++;
        }
    }

    public void openCreateTokenDialog() {
        FormDialogHandler handler = values -> {
            createTokenAction.execute(values.get(CreateTokenDialogConfiguration.NAME),
                    values.get(CreateTokenDialogConfiguration.VALUE));
            loadTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new CreateTokenDialogConfiguration(), handler);
    }

    public void openUpdateTokenDialog() {
        TokenProjection token = getSelectedToken();

        if (token == null) {
            return;
        }

        FormDialogHandler handler = values -> {
            updateTokenAction.execute(token.id(), values.get(UpdateTokenDialogConfiguration.NAME),
                    values.get(UpdateTokenDialogConfiguration.VALUE));
            loadTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new UpdateTokenDialogConfiguration(token), handler);
    }

    public void openDeleteTokenDialog() {
        TokenProjection token = getSelectedToken();
        if (token == null) {
            return;
        }

        ConfirmDialogHandler handler = () -> {
            deleteTokenAction.execute(token.id());
            loadTokens();
        };

        dialogManager.openDialog(DialogType.CONFIRM, new DeleteTokenDialogConfiguration(token), handler);
    }

    private void loadTokens() {
        tokens = getTokensQuery.execute();
    }
}
