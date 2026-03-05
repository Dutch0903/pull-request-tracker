package com.prtracker.presentation.cli;

import org.springframework.shell.jline.tui.component.view.event.KeyEvent;

import java.util.function.Consumer;

public record KeyBinding(
        int key,
        String description,
        Consumer<KeyEvent> action
) {
    public static KeyBinding create(
            int key,
            String description,
            Consumer<KeyEvent> action
    ) {
        return new KeyBinding(key, description, action);
    }
}
