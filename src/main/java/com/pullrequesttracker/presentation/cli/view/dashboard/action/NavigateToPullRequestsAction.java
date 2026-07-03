package com.pullrequesttracker.presentation.cli.view.dashboard.action;

import com.pullrequesttracker.presentation.cli.action.KeyAction;
import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateToPullRequestsAction implements KeyAction {
    private final NavigationEventPublisher navigationEventPublisher;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCharIgnoreCase('p');
    }

    @Override
    public String getKey() {
        return "p";
    }

    @Override
    public String getLabel() {
        return "Pull requests";
    }

    @Override
    public void execute() {
        navigationEventPublisher.navigateTo(ViewName.PULL_REQUESTS);
    }
}
