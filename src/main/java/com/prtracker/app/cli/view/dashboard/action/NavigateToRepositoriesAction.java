package com.prtracker.app.cli.view.dashboard.action;

import com.prtracker.app.cli.action.KeyAction;
import com.prtracker.app.cli.navigation.NavigationEventPublisher;
import com.prtracker.app.cli.navigation.ViewName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateToRepositoriesAction implements KeyAction {
    private final NavigationEventPublisher navigationEventPublisher;

    @Override
    public char getKey() {
        return 'r';
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
