package com.pullrequesttracker.presentation.cli.action;

import com.pullrequesttracker.presentation.cli.navigation.RefreshEventPublisher;
import dev.tamboui.tui.event.KeyCode;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshAction implements SharedAction {
    private final RefreshEventPublisher refreshEventPublisher;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isKey(KeyCode.F5);
    }

    @Override
    public String getKey() {
        return "F5";
    }

    @Override
    public String getLabel() {
        return "Refresh";
    }

    @Override
    public int order() {
        return Integer.MAX_VALUE - 1;
    }

    @Override
    public void execute(KeyEvent event) {
        refreshEventPublisher.refresh();
    }
}
