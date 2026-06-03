package com.pullrequesttracker.presentation.cli;

import com.pullrequesttracker.presentation.cli.navigation.ViewManager;
import dev.tamboui.toolkit.app.ToolkitRunner;
import dev.tamboui.tui.TuiConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class PullRequestTracker implements SmartInitializingSingleton {
    private final ViewManager viewManager;
    private final ConfigurableApplicationContext context;

    @Override
    public void afterSingletonsInstantiated() {
        Thread tuiThread = new Thread(() -> {
            try (ToolkitRunner runner = ToolkitRunner.create(configure())) {
                runner.run(viewManager::getCurrentView);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                context.close();
            }
        }, "tui-thread");
        tuiThread.setDaemon(true);
        tuiThread.start();
    }

    private TuiConfig configure() {
        return TuiConfig.builder().mouseCapture(false).tickRate(Duration.ofMillis(16)).build();
    }
}
