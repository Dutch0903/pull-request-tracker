package com.prtracker.app.cli.view.token.action;

import com.prtracker.app.cli.action.KeyAction;
import com.prtracker.app.cli.view.token.dialog.DeleteTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import org.springframework.stereotype.Component;

@Component
public class OpenDeleteTokenDialog implements KeyAction {
    private final DeleteTokenDialogAction deleteTokenDialogAction;

    public OpenDeleteTokenDialog(DeleteTokenDialogAction deleteTokenDialogAction) {
        this.deleteTokenDialogAction = deleteTokenDialogAction;
    }

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isDeleteBackward();
    }

    @Override
    public String getKey() {
        return "←";
    }

    @Override
    public String getLabel() {
        return "Delete Token";
    }

    @Override
    public void execute() {
deleteTokenDialogAction.open();
    }
}
