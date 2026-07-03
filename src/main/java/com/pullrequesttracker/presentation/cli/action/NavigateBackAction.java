package com.pullrequesttracker.presentation.cli.action;

import com.pullrequesttracker.presentation.cli.navigation.NavigationEventPublisher;
import com.pullrequesttracker.presentation.cli.navigation.ViewStack;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NavigateBackAction implements SharedAction {
    private final NavigationEventPublisher navigationEventPublisher;
    private final ViewStack viewStack;

    @Override
    public boolean isAvailable() {
        return viewStack.size() > 1;
    }

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
    public int order() {
        return Integer.MAX_VALUE;
    }

    @Override
    public void execute(KeyEvent event) {
        navigationEventPublisher.navigateBack();
    }
}
