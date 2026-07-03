package com.pullrequesttracker.presentation.cli.view.dashboard.action;

import com.pullrequesttracker.presentation.cli.action.DashboardViewAction;
import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateToRepositoriesAction implements DashboardViewAction {
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
    public int order() {
        return 0;
    }

    @Override
    public void execute(KeyEvent event) {
        navigationEventPublisher.navigateTo(ViewName.REPOSITORIES);
    }
}
