package com.prtracker.application.query;

public abstract class VoidQuery<TOutput> extends Query<Void, TOutput> {
    public TOutput execute() {
        return execute(null);
    }

    @Override
    protected TOutput executeInternal(Void input) {
        return executeInternal();
    }

    protected abstract TOutput executeInternal();
}
