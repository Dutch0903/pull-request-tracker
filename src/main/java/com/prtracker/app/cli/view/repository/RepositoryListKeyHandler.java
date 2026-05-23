package com.prtracker.app.cli.view.repository;

import com.prtracker.app.cli.action.KeyHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RepositoryListKeyHandler extends KeyHandler {
    public RepositoryListKeyHandler() {
        super(List.of());
    }

    // private final NavigationEventPublisher navigationEventPublisher;
    // private final CreateRepositoryDialogAction createRepositoryDialogAction;
    //
    // public EventResult handle(KeyEvent event) {
    // if (event.isCharIgnoreCase('d')) {
    // navigationEventPublisher.navigateTo(ViewName.DASHBOARD);
    // return EventResult.HANDLED;
    // }
    //
    // if (event.isCharIgnoreCase('c')) {
    // createRepositoryDialogAction.open();
    // return EventResult.HANDLED;
    // }
    //
    // return EventResult.UNHANDLED;
    // }
}
