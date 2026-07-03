package com.pullrequesttracker.presentation.cli.action;

import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public abstract class KeyHandler {
    private final List<KeyAction> actions;

    protected KeyHandler(List<SharedAction> shared, List<? extends KeyAction> viewActions) {
        this.actions = Stream.concat(shared.stream(), viewActions.stream())
                .sorted(Comparator.comparingInt(KeyAction::order)).toList();
    }

    public List<KeyBinding> getBindings() {
        return actions.stream().filter(KeyAction::isAvailable)
                .map(action -> new KeyBinding(action.getKey(), action.getLabel())).toList();
    }

    public EventResult handle(KeyEvent event) {
        return actions.stream().filter(KeyAction::isAvailable).filter(action -> action.matches(event)).findFirst()
                .map(action -> {
                    action.execute(event);
                    return EventResult.HANDLED;
                }).orElse(EventResult.UNHANDLED);
    }
}
