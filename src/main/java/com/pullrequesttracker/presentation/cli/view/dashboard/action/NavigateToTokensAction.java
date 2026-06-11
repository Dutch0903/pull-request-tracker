package com.pullrequesttracker.presentation.cli.view.dashboard.action;

import com.pullrequesttracker.presentation.cli.action.KeyAction;
import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateToTokensAction implements KeyAction {
    private final NavigationEventPublisher navigationEventPublisher;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCharIgnoreCase('t');
    }

    @Override
    public String getKey() {
        return "t";
    }

    @Override
    public String getLabel() {
        return "Tokens";
    }

    @Override
    public void execute() {
        navigationEventPublisher.navigateTo(ViewName.TOKENS);
    }
}
