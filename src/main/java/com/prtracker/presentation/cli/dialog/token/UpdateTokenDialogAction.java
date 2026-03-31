package com.prtracker.presentation.cli.dialog.token;

import com.prtracker.application.command.UpdateTokenCommand;
import com.prtracker.application.command.dto.UpdateTokenDto;
import com.prtracker.application.query.dto.TokenProjection;
import com.prtracker.presentation.cli.dialog.DialogAction;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.dialog.DialogType;
import com.prtracker.presentation.cli.dialog.form.FormDialogHandler;
import com.prtracker.presentation.cli.view.token.TokenManagerState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateTokenDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final TokenManagerState tokenManagerState;
    private final UpdateTokenCommand updateTokenCommand;

    @Override
    public void open() {
        TokenProjection token = tokenManagerState.getSelectedToken();

        if (token == null) {
            return;
        }

        FormDialogHandler handler = values -> {
            updateTokenCommand.execute(new UpdateTokenDto(token.id(), values.get(TokenFormFields.NAME),
                    values.get(TokenFormFields.VALUE)));
            tokenManagerState.refreshTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new UpdateTokenDialogConfiguration(token), handler);
    }
}
