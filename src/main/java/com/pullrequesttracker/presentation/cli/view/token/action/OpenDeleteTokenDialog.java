package com.pullrequesttracker.presentation.cli.view.token.action;

import com.pullrequesttracker.presentation.cli.action.KeyAction;
import com.pullrequesttracker.presentation.cli.view.token.dialog.DeleteTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenDeleteTokenDialog implements KeyAction {
    private final DeleteTokenDialogAction deleteTokenDialogAction;

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
