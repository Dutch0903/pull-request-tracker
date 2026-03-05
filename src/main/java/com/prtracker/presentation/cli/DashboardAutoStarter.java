package com.prtracker.presentation.cli;

import com.prtracker.presentation.cli.command.DashboardCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardAutoStarter implements CommandLineRunner {
    private final DashboardCommand dashboardCommand;
    private final ApplicationContext applicationContext;

    public void run(String... args) {
        dashboardCommand.run();

        int exitCode = SpringApplication.exit(applicationContext);
        System.exit(exitCode);
    }
}
