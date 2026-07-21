package com.pullrequesttracker.presentation.cli.action;

import dev.tamboui.tui.event.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class OpenInBrowserAction implements KeyAction {
    private static final Logger log = LoggerFactory.getLogger(OpenInBrowserAction.class);

    protected abstract String getUrl();

    @Override
    public boolean matches(KeyEvent keyEvent) {
        return keyEvent.isConfirm();
    }

    @Override
    public String getKey() {
        return "Enter";
    }

    @Override
    public String getLabel() {
        return "Open";
    }

    @Override
    public int order() {
        return 0;
    }

    @Override
    public boolean isAvailable() {
        return getUrl() != null;
    }

    @Override
    public void execute(KeyEvent event) {
        String url = getUrl();
        try {
            new ProcessBuilder(browserCommand(), url)
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .redirectError(ProcessBuilder.Redirect.DISCARD)
                    .start();
        } catch (Exception e) {
            log.warn("Failed to open URL in browser: {}", url, e);
        }
    }

    private String browserCommand() {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac") || os.contains("darwin")) {
            return "open";
        }
        if (os.contains("win")) {
            return "explorer";
        }
        return "xdg-open";
    }
}
