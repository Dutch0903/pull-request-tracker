package com.pullrequesttracker.presentation.cli.view.repository.action;

import com.pullrequesttracker.presentation.cli.action.KeyAction;
import com.pullrequesttracker.presentation.cli.view.repository.CreateRepositoryDialogAction;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenCreateRepositoryDialog implements KeyAction {
    private final CreateRepositoryDialogAction createRepositoryDialogAction;

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isCharIgnoreCase('c');
    }

    @Override
    public String getKey() {
        return "c";
    }

    @Override
    public String getLabel() {
        return "Create Repository";
    }

    @Override
    public void execute() {
        createRepositoryDialogAction.open();
    }
}
