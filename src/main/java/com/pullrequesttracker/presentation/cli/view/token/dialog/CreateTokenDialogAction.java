package com.pullrequesttracker.presentation.cli.view.token.dialog;

import com.pullrequesttracker.application.usecase.CreateToken;
import com.pullrequesttracker.presentation.cli.dialog.DialogAction;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogHandler;
import com.pullrequesttracker.presentation.cli.view.token.TokenFormFields;
import com.pullrequesttracker.presentation.cli.view.token.TokenManagerState;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;
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
            createToken.execute(
                    TokenName.from(values.get(TokenFormFields.NAME)),
                    new TokenValue(values.get(TokenFormFields.VALUE)));
            tokenManagerState.refreshTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new CreateTokenDialogConfiguration(), handler);
    }
}
