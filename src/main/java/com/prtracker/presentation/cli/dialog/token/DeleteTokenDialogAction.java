package com.prtracker.presentation.cli.dialog.token;

import com.prtracker.application.command.DeleteTokenCommand;
import com.prtracker.application.command.dto.DeleteTokenDto;
import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.presentation.cli.dialog.DialogAction;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.dialog.DialogType;
import com.prtracker.presentation.cli.dialog.confirm.ConfirmDialogHandler;
import com.prtracker.presentation.cli.view.token.TokenManagerState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteTokenDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final TokenManagerState tokenManagerState;
    private final DeleteTokenCommand deleteTokenCommand;

    @Override
    public void open() {
        TokenProjection token = tokenManagerState.getSelectedToken();
        if (token == null) {
            return;
        }

        ConfirmDialogHandler handler = () -> {
            deleteTokenCommand.execute(new DeleteTokenDto(token.id()));
            tokenManagerState.refreshTokens();
        };

        dialogManager.openDialog(DialogType.CONFIRM, new DeleteTokenDialogConfiguration(token), handler);
    }
}
