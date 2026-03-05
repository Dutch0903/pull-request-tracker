package com.prtracker.presentation.cli.command;

import com.prtracker.presentation.cli.screen.ScreenType;
import com.prtracker.presentation.cli.view.ViewManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DashboardCommand {
    private final ViewManager viewManager;

    @Command(description = "Start the interactive dashboard")
    public void run() {
        viewManager.start(ScreenType.HOME);
    }
}
