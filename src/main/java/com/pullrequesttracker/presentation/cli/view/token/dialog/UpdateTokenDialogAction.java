package com.pullrequesttracker.presentation.cli.view.token.dialog;

import com.pullrequesttracker.application.query.TokenProjection;
import com.pullrequesttracker.application.usecase.UpdateToken;
import com.pullrequesttracker.presentation.cli.dialog.DialogAction;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogHandler;
import com.pullrequesttracker.presentation.cli.view.token.TokenFormFields;
import com.pullrequesttracker.presentation.cli.view.token.TokenManagerState;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;
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
        if (token == null) return;

        FormDialogHandler handler = values -> {
            updateToken.execute(
                    TokenId.from(token.id()),
                    TokenName.from(values.get(TokenFormFields.NAME)),
                    new TokenValue(values.get(TokenFormFields.VALUE)));
            tokenManagerState.refreshTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new UpdateTokenDialogConfiguration(token), handler);
    }
}
