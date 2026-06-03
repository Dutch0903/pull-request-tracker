package com.pullrequesttracker.presentation.cli.view.dashboard.action;

import com.pullrequesttracker.presentation.cli.action.KeyAction;
import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateToRepositoriesAction implements KeyAction {
    private final NavigationEventPublisher navigationEventPublisher;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCharIgnoreCase('r');
    }

    @Override
    public String getKey() {
        return "r";
    }

    @Override
    public String getLabel() {
        return "Repositories";
    }

    @Override
    public void execute() {
        navigationEventPublisher.navigateTo(ViewName.REPOSITORIES);
    }
}
