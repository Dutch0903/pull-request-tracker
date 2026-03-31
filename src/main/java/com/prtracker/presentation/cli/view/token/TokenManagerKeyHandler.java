package com.prtracker.presentation.cli.view.token;

import com.prtracker.presentation.cli.ViewName;
import com.prtracker.presentation.cli.dialog.token.CreateTokenDialogAction;
import com.prtracker.presentation.cli.dialog.token.DeleteTokenDialogAction;
import com.prtracker.presentation.cli.dialog.token.UpdateTokenDialogAction;
import com.prtracker.presentation.cli.event.NavigationEventPublisher;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenManagerKeyHandler {
    private final TokenManagerState state;
    private final NavigationEventPublisher navigationEventPublisher;
    private final CreateTokenDialogAction createTokenDialogAction;
    private final UpdateTokenDialogAction updateTokenDialogAction;
    private final DeleteTokenDialogAction deleteTokenDialogAction;

    public EventResult handle(KeyEvent event) {
        if (event.isCharIgnoreCase('d')) {
            navigationEventPublisher.navigateTo(ViewName.DASHBOARD);
            return EventResult.HANDLED;
        }

        if (event.isCharIgnoreCase('c')) {
            createTokenDialogAction.open();
            return EventResult.HANDLED;
        }

        if (event.isCharIgnoreCase('u')) {
            updateTokenDialogAction.open();
            return EventResult.HANDLED;
        }

        if (event.isDeleteBackward()) {
            deleteTokenDialogAction.open();
            return EventResult.HANDLED;
        }

        if (event.isDown()) {
            state.selectNext();
            return EventResult.HANDLED;
        }

        if (event.isUp()) {
            state.selectPrevious();
            return EventResult.HANDLED;
        }

        return EventResult.UNHANDLED;
    }
}
