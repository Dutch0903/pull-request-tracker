package com.pullrequesttracker.presentation.cli.action;

import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateBackAction implements KeyAction {
    private final NavigationEventPublisher navigationEventPublisher;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCancel();
    }

    @Override
    public String getKey() {
        return "Esc";
    }

    @Override
    public String getLabel() {
        return "Back";
    }

    @Override
    public void execute() {
        navigationEventPublisher.navigateBack();
    }
}
