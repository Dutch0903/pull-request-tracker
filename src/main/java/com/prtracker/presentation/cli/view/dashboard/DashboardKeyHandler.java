package com.prtracker.presentation.cli.view.dashboard;

import com.prtracker.presentation.cli.View;
import com.prtracker.presentation.cli.event.NavigationEventPublisher;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardKeyHandler {
    private final NavigationEventPublisher navigationEventPublisher;

    public EventResult handle(KeyEvent event) {
        if (event.isCharIgnoreCase('r')) {
            navigationEventPublisher.navigateTo(View.REPOSITORIES);
            return EventResult.HANDLED;
        }

        return EventResult.UNHANDLED;
    }
}
