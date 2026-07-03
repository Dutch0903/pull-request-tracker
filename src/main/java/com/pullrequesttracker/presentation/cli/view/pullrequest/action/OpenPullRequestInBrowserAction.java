package com.pullrequesttracker.presentation.cli.view.pullrequest.action;

import com.pullrequesttracker.application.dto.PullRequestListItemDto;
import com.pullrequesttracker.presentation.cli.action.PullRequestListViewAction;
import com.pullrequesttracker.presentation.cli.view.pullrequest.component.PullRequestList;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenPullRequestInBrowserAction implements PullRequestListViewAction {
    private static final Logger log = LoggerFactory.getLogger(OpenPullRequestInBrowserAction.class);

    private final PullRequestList pullRequestList;

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
        return pullRequestList.getSelectedItem() != null;
    }

    @Override
    public void execute(KeyEvent event) {
        PullRequestListItemDto pr = pullRequestList.getSelectedItem();
        try {
            new ProcessBuilder(browserCommand(), pr.url())
                    .redirectOutput(ProcessBuilder.Redirect.DISCARD)
                    .redirectError(ProcessBuilder.Redirect.DISCARD)
                    .start();
        } catch (Exception e) {
            log.warn("Failed to open pull request in browser: {}", pr.url(), e);
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
