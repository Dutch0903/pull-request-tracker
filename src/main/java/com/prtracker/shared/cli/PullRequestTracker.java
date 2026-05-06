package com.prtracker.shared.cli;

import dev.tamboui.toolkit.app.ToolkitRunner;
import dev.tamboui.tui.TuiConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PullRequestTracker implements SmartInitializingSingleton {
    private final ViewManager viewManager;

    public void run() throws Exception {
        try (ToolkitRunner runner = ToolkitRunner.create(configure())) {
            runner.run(viewManager::getCurrentView);
        }
    }

    public void afterSingletonsInstantiated() {
        try {
            run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private TuiConfig configure() {
        return TuiConfig.builder().mouseCapture(false).tickRate(Duration.ofMillis(16)) // 60 fps
                .build();
    }
}
