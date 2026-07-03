package com.pullrequesttracker.presentation.cli.view.token.action;

import com.pullrequesttracker.presentation.cli.action.TokenManagerViewAction;
import com.pullrequesttracker.presentation.cli.view.token.dialog.CreateTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenCreateTokenDialog implements TokenManagerViewAction {
    private final CreateTokenDialogAction createTokenDialogAction;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCharIgnoreCase('c');
    }

    @Override
    public String getKey() {
        return "c";
    }

    @Override
    public String getLabel() {
        return "Create Token";
    }

    @Override
    public int order() {
        return 1;
    }

    @Override
    public void execute(KeyEvent event) {
        createTokenDialogAction.open();
    }
}
