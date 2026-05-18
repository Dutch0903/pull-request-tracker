package com.prtracker.app.cli.view.dashboard;

import com.prtracker.app.cli.navigation.ViewName;
import com.prtracker.app.cli.event.NavigationEventPublisher;
import com.prtracker.coderepository.application.command.CheckRepositories;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardKeyHandler {
    private final NavigationEventPublisher navigationEventPublisher;
    private final DashboardController dashboardController;
    private final CheckRepositories checkRepositories;

    public EventResult handle(KeyEvent event) {
        if (event.isCharIgnoreCase('r')) {
            navigationEventPublisher.navigateTo(ViewName.REPOSITORIES);
            return EventResult.HANDLED;
        }

        if (event.isCharIgnoreCase('t')) {
            navigationEventPublisher.navigateTo(ViewName.TOKENS);
            return EventResult.HANDLED;
        }

        if (event.isCharIgnoreCase('f')) {
            checkRepositories.execute();
            return EventResult.HANDLED;
        }

        return EventResult.UNHANDLED;
    }
}
