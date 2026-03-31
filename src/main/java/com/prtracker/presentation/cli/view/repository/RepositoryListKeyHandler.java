package com.prtracker.presentation.cli.view.repository;

import com.prtracker.presentation.cli.ViewName;
import com.prtracker.presentation.cli.event.NavigationEventPublisher;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RepositoryListKeyHandler {
    private final NavigationEventPublisher navigationEventPublisher;
    private final CreateRepositoryDialogAction createRepositoryDialogAction;

    public EventResult handle(KeyEvent event) {
        if (event.isCharIgnoreCase('d')) {
            navigationEventPublisher.navigateTo(ViewName.DASHBOARD);
            return EventResult.HANDLED;
        }

        if (event.isCharIgnoreCase('c')) {
            createRepositoryDialogAction.open();
            return EventResult.HANDLED;
        }

        return EventResult.UNHANDLED;
    }
}
