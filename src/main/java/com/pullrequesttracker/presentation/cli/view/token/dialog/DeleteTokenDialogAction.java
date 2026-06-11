package com.pullrequesttracker.presentation.cli.view.token.dialog;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.application.usecase.DeleteToken;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.presentation.cli.dialog.DialogAction;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.dialog.DialogType;
import com.pullrequesttracker.presentation.cli.dialog.confirm.ConfirmDialogHandler;
import com.pullrequesttracker.presentation.cli.view.token.TokenManagerController;
import com.pullrequesttracker.presentation.cli.view.token.component.TokenList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteTokenDialogAction implements DialogAction {
    private final DialogManager dialogManager;
    private final TokenManagerController tokenManagerController;
    private final TokenList tokenList;
    private final DeleteToken deleteToken;

    @Override
    public void open() {
        TokenDto token = tokenList.getSelectedToken();
        if (token == null)
            return;

        ConfirmDialogHandler handler = () -> {
            deleteToken.execute(TokenId.from(token.id()));
            tokenManagerController.loadTokens();
        };

        dialogManager.openDialog(DialogType.CONFIRM, new DeleteTokenDialogConfiguration(token), handler);
    }
}
