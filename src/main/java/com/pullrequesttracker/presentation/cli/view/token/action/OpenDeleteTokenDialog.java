package com.pullrequesttracker.presentation.cli.view.token.action;

import com.pullrequesttracker.presentation.cli.action.TokenManagerViewAction;
import com.pullrequesttracker.presentation.cli.view.token.dialog.DeleteTokenDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenDeleteTokenDialog implements TokenManagerViewAction {
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
    public int order() {
        return 3;
    }

    @Override
    public void execute(KeyEvent event) {
        deleteTokenDialogAction.open();
    }
}
