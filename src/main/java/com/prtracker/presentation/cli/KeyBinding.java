package com.prtracker.presentation.cli;

public record KeyBinding(String key, String label) {
	public static KeyBinding create(String key, String label) {
		return new KeyBinding(key, label);
	}

	@Override
	public String toString() {
		return "[" + key + "] " + label;
	}
}
