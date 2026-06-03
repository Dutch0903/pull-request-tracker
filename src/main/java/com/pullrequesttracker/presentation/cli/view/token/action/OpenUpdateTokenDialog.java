package com.pullrequesttracker.presentation.cli.view.token.action;

import com.pullrequesttracker.presentation.cli.action.KeyAction;
import com.pullrequesttracker.presentation.cli.view.token.dialog.UpdateTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenUpdateTokenDialog implements KeyAction {
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
    public void execute() {
        updateTokenDialogAction.open();
    }
}
