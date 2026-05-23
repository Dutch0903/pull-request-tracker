package com.prtracker.app.cli.action;

public record KeyBinding(String key, String label) {
    @Override
    public String toString() {
        return "[" + key + "] " + label;
    }
}
