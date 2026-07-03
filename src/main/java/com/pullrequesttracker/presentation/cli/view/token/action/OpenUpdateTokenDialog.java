package com.pullrequesttracker.presentation.cli.view.token.action;

import com.pullrequesttracker.presentation.cli.action.TokenManagerViewAction;
import com.pullrequesttracker.presentation.cli.view.token.dialog.UpdateTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenUpdateTokenDialog implements TokenManagerViewAction {
    private final UpdateTokenDialogAction updateTokenDialogAction;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCharIgnoreCase('u');
    }

    @Override
    public String getKey() {
        return "u";
    }

    @Override
    public String getLabel() {
        return "Update Token";
    }

    @Override
    public int order() {
        return 2;
    }

    @Override
    public void execute(KeyEvent event) {
        updateTokenDialogAction.open();
    }
}
