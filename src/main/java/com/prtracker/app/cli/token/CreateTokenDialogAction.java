package com.prtracker.app.cli.token;

import com.prtracker.app.cli.dialog.DialogAction;
import com.prtracker.app.cli.dialog.DialogManager;
import com.prtracker.app.cli.dialog.DialogType;
import com.prtracker.app.cli.dialog.form.FormDialogHandler;
import com.prtracker.token.application.command.CreateToken;
import com.prtracker.token.application.command.CreateTokenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTokenDialogAction implements DialogAction {
    private final TokenManagerState tokenManagerState;
    private final DialogManager dialogManager;
    private final CreateToken createToken;

    @Override
    public void open() {
        FormDialogHandler handler = values -> {
            createToken
                    .execute(new CreateTokenDto(values.get(TokenFormFields.NAME), values.get(TokenFormFields.VALUE)));
            tokenManagerState.refreshTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new CreateTokenDialogConfiguration(), handler);
    }
}
