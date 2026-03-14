package com.prtracker.application.command;

public abstract class VoidCommand extends Command<Void, Void> {
    public void execute() {
        execute(null);
    }

    @Override
    protected Void executeInternal(Void input) {
        executeInternal();
        return null;
    }

    protected abstract void executeInternal();
}
