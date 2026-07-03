package com.pullrequesttracker.presentation.cli.view.token.action;

import com.pullrequesttracker.presentation.cli.action.TokenManagerViewAction;
import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateToDashboard implements TokenManagerViewAction {
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
    public int order() {
        return 0;
    }

    @Override
    public void execute(KeyEvent event) {
        navigationEventPublisher.navigateTo(ViewName.DASHBOARD);
    }
}
