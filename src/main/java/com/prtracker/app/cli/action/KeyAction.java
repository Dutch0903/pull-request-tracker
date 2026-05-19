package com.prtracker.app.cli.action;

public interface KeyAction {
    char getKey();
    String getLabel();
    void execute();
}
