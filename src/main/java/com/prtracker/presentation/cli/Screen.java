package com.prtracker.presentation.cli;

public enum Screen {
    DASHBOARD("dashboard"),
    ADD_REPOSITORY("add-repository");

    private final String text;

    Screen(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }
}
