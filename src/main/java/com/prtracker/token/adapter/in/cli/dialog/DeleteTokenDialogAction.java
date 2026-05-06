package com.prtracker.token.adapter.in.cli.dialog;

import com.prtracker.shared.cli.dialog.DialogAction;
import com.prtracker.shared.cli.dialog.DialogManager;
import com.prtracker.shared.cli.dialog.DialogType;
import com.prtracker.shared.cli.dialog.confirm.ConfirmDialogHandler;
import com.prtracker.token.adapter.in.cli.view.TokenManagerState;
import com.prtracker.token.application.command.DeleteToken;
import com.prtracker.token.application.command.DeleteTokenDto;
import com.prtracker.token.application.query.TokenProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteTokenDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final TokenManagerState tokenManagerState;
    private final DeleteToken deleteToken;

    @Override
    public void open() {
        TokenProjection token = tokenManagerState.getSelectedToken();
        if (token == null) {
            return;
        }

        ConfirmDialogHandler handler = () -> {
            deleteToken.execute(new DeleteTokenDto(token.id()));
            tokenManagerState.refreshTokens();
        };

        dialogManager.openDialog(DialogType.CONFIRM, new DeleteTokenDialogConfiguration(token), handler);
    }
}
