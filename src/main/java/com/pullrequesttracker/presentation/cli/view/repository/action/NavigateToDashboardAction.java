package com.pullrequesttracker.presentation.cli.view.repository.action;

import com.pullrequesttracker.presentation.cli.action.KeyAction;
import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateToDashboardAction implements KeyAction {
    private final NavigationEventPublisher navigationEventPublisher;

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
