package com.prtracker.presentation.cli.view.token;

import com.prtracker.presentation.cli.ViewName;
import com.prtracker.presentation.cli.event.NavigationEventPublisher;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenManagerKeyHandler {
    private final TokenManagerController controller;
    private final NavigationEventPublisher navigationEventPublisher;

    public EventResult handle(KeyEvent event) {
        if (event.isCharIgnoreCase('d')) {
            navigationEventPublisher.navigateTo(ViewName.DASHBOARD);
            return EventResult.HANDLED;
        }

        if (event.isCharIgnoreCase('c')) {
            controller.openCreateTokenDialog();
            return EventResult.HANDLED;
        }

        if (event.isCharIgnoreCase('u')) {
            controller.openUpdateTokenDialog();
            return EventResult.HANDLED;
        }

        if (event.isDeleteBackward()) {
            controller.openDeleteTokenDialog();
            return EventResult.HANDLED;
        }

        if (event.isDown()) {
            controller.selectNext();
            return EventResult.HANDLED;
        }

        if (event.isUp()) {
            controller.selectPrevious();
            return EventResult.HANDLED;
        }

        return EventResult.UNHANDLED;
    }
}
