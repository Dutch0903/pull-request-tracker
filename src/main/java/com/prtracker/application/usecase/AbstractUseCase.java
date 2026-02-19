package com.prtracker.application.usecase;

public abstract class AbstractUseCase<I, O> {
    protected abstract O executeInternal(I input);

    public O execute(I input) {
        return executeInternal(input);
    }
}
