package com.prtracker.presentation.cli.dialog.token;

import com.prtracker.application.command.CreateTokenCommand;
import com.prtracker.application.command.dto.CreateTokenDto;
import com.prtracker.presentation.cli.dialog.DialogAction;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.dialog.DialogType;
import com.prtracker.presentation.cli.dialog.form.FormDialogHandler;
import com.prtracker.presentation.cli.view.token.TokenManagerState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateTokenDialogAction implements DialogAction {
    private final TokenManagerState tokenManagerState;
    private final DialogManager dialogManager;
    private final CreateTokenCommand createTokenCommand;

    @Override
    public void open() {
        FormDialogHandler handler = values -> {
            createTokenCommand.execute(new CreateTokenDto(values.get(TokenFormFields.NAME),
                    values.get(TokenFormFields.VALUE)));
            tokenManagerState.refreshTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new CreateTokenDialogConfiguration(), handler);
    }
}
