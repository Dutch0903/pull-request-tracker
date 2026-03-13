package com.prtracker.application.command;

public abstract class Command<TInput, TOutput> {
	public TOutput execute(TInput input) {
		validate(input);
		return executeInternal(input);
	}

	protected void validate(TInput input) {
		// Optional validation hook for subclasses
	}

	protected abstract TOutput executeInternal(TInput input);
}
