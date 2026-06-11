package com.pullrequesttracker.presentation.cli.view.token.dialog;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.application.usecase.UpdateToken;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.domain.valueobject.TokenName;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import com.pullrequesttracker.presentation.cli.dialog.DialogAction;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import com.pullrequesttracker.presentation.cli.dialog.form.FormDialogHandler;
import com.pullrequesttracker.presentation.cli.view.token.TokenFormFields;
import com.pullrequesttracker.presentation.cli.view.token.TokenManagerController;
import com.pullrequesttracker.presentation.cli.view.token.component.TokenList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateTokenDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final TokenManagerController tokenManagerController;
    private final TokenList tokenList;
    private final UpdateToken updateToken;

    @Override
    public void open() {
        TokenDto token = tokenList.getSelectedToken();
        if (token == null)
            return;

        FormDialogHandler handler = values -> {
            updateToken.execute(TokenId.from(token.id()), TokenName.from(values.get(TokenFormFields.NAME)),
                    new TokenValue(values.get(TokenFormFields.VALUE)));
            tokenManagerController.loadTokens();
        };

        dialogManager.openDialog(DialogType.FORM, new UpdateTokenDialogConfiguration(token), handler);
    }
}
