package com.prtracker.app.cli.action;

import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import java.util.List;

public abstract class KeyHandler {
    private final List<KeyAction> actions;

    protected KeyHandler(List<KeyAction> actions) {
        this.actions = actions;
    }

    public List<KeyBinding> getBindings() {
        return actions.stream().map(action -> new KeyBinding(action.getKey(), action.getLabel())).toList();
    }

    public EventResult handle(KeyEvent event) {
        return actions.stream()
                .filter(keyAction -> event.isCharIgnoreCase(keyAction.getKey()))
                .findFirst()
                .map(keyAction -> {
                    keyAction.execute();
                    return EventResult.HANDLED;
                        }
                ).orElse(EventResult.UNHANDLED);
    }
}
