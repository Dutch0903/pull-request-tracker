package com.pullrequesttracker.presentation.cli.action;

public record KeyBinding(String key, String label) {
    @Override
    public String toString() {
        return "[" + key + "] " + label;
    }
}
