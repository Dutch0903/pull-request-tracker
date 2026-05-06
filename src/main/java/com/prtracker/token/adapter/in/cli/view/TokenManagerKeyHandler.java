package com.prtracker.token.adapter.in.cli.view;

import com.prtracker.shared.cli.ViewName;
import com.prtracker.shared.cli.event.NavigationEventPublisher;
import com.prtracker.token.adapter.in.cli.dialog.CreateTokenDialogAction;
import com.prtracker.token.adapter.in.cli.dialog.DeleteTokenDialogAction;
import com.prtracker.token.adapter.in.cli.dialog.UpdateTokenDialogAction;
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
