package com.pullrequesttracker.presentation.cli.view.token.action;

import com.pullrequesttracker.presentation.cli.action.KeyAction;
import com.pullrequesttracker.presentation.cli.view.token.dialog.CreateTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenCreateTokenDialog implements KeyAction {
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
    public void execute() {
        createTokenDialogAction.open();
    }
}
