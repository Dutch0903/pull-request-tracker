package com.prtracker.application.query;

public abstract class Query<TInput, TOutput> {
    public TOutput execute(TInput input) {
        return executeInternal(input);
    }

    protected abstract TOutput executeInternal(TInput input);
}
