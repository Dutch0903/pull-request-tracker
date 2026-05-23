package com.prtracker.app.cli.view.token.action;

import com.prtracker.app.cli.action.KeyAction;
import com.prtracker.app.cli.view.token.dialog.CreateTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import org.springframework.stereotype.Component;

@Component
public class OpenCreateTokenDialog implements KeyAction {
    private final CreateTokenDialogAction createTokenDialogAction;

    public OpenCreateTokenDialog(CreateTokenDialogAction createTokenDialogAction) {
        this.createTokenDialogAction = createTokenDialogAction;
    }

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
