package com.prtracker.app.cli.action;

import dev.tamboui.tui.event.KeyEvent;

public interface KeyAction {
    boolean matches(KeyEvent keyEvent);
    String getKey();
    String getLabel();
    void execute();
}
