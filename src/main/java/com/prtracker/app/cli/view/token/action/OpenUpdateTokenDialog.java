package com.prtracker.app.cli.view.token.action;

import com.prtracker.app.cli.action.KeyAction;
import com.prtracker.app.cli.view.token.dialog.UpdateTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import org.springframework.stereotype.Component;

@Component
public class OpenUpdateTokenDialog implements KeyAction {
    private final UpdateTokenDialogAction updateTokenDialogAction;

    public OpenUpdateTokenDialog(UpdateTokenDialogAction updateTokenDialogAction) {
        this.updateTokenDialogAction = updateTokenDialogAction;
    }

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
