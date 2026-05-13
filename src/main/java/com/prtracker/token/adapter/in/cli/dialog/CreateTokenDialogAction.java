package com.prtracker.token.adapter.in.cli.dialog;

import com.prtracker.shared.cli.dialog.DialogAction;
import com.prtracker.shared.cli.dialog.DialogManager;
import com.prtracker.shared.cli.dialog.DialogType;
import com.prtracker.shared.cli.dialog.form.FormDialogHandler;
import com.prtracker.token.adapter.in.cli.view.TokenManagerState;
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
