package com.prtracker.application.usecase;

public abstract class AbstractVoidUseCase extends AbstractUseCase<Void, Void> {
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
