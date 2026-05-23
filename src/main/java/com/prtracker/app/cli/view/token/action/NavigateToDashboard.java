package com.prtracker.app.cli.view.token.action;

import com.prtracker.app.cli.action.KeyAction;
import com.prtracker.app.cli.navigation.NavigationEventPublisher;
import com.prtracker.app.cli.navigation.ViewName;
import dev.tamboui.tui.event.KeyEvent;
import org.springframework.stereotype.Component;

@Component
public class NavigateToDashboard implements KeyAction {
    private final NavigationEventPublisher navigationEventPublisher;

    public NavigateToDashboard(NavigationEventPublisher navigationEventPublisher) {
        this.navigationEventPublisher = navigationEventPublisher;
    }

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCharIgnoreCase('d');
    }

    @Override
    public String getKey() {
        return "d";
    }

    @Override
    public String getLabel() {
        return "Dashboard";
    }

    @Override
    public void execute() {
        navigationEventPublisher.navigateTo(ViewName.DASHBOARD);
    }
}
