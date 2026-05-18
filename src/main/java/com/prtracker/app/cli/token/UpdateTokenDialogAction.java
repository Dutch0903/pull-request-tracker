package com.prtracker.app.cli.token;

import com.prtracker.app.cli.dialog.DialogAction;
import com.prtracker.app.cli.dialog.DialogManager;
import com.prtracker.app.cli.dialog.DialogType;
import com.prtracker.app.cli.dialog.form.FormDialogHandler;
import com.prtracker.token.application.command.UpdateToken;
import com.prtracker.token.application.command.UpdateTokenDto;
import com.prtracker.token.application.query.TokenProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateTokenDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final TokenManagerState tokenManagerState;
    private final UpdateToken updateToken;

    @Override
    public void open() {
        TokenProjection token = tokenManagerState.getSelectedToken();

        if (token == null) {
            return;
        }

        FormDialogHandler handler = values -> {
            updateToken.execute(new UpdateTokenDto(token.id(), values.get(TokenFormFields.NAME),
                    values.get(TokenFormFields.VALUE)));
            tokenManagerState.refreshTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new UpdateTokenDialogConfiguration(token), handler);
    }
}
