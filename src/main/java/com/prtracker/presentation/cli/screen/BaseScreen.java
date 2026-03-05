package com.prtracker.presentation.cli.screen;

import com.prtracker.presentation.cli.KeyBinding;
import com.prtracker.presentation.cli.NavigationCallback;
import com.prtracker.presentation.cli.view.QuitCallback;
import org.springframework.shell.jline.tui.component.view.control.View;
import org.springframework.shell.jline.tui.component.view.event.EventLoop;
import org.springframework.shell.jline.tui.component.view.event.KeyEvent;
import reactor.core.Disposable;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

public abstract class BaseScreen {
    protected NavigationCallback navigationCallback;
    protected QuitCallback quitCallback;
    private static final Duration DEBOUNCE_DURATION = Duration.ofMillis(300);

    public abstract View render();

    public abstract ScreenType getType();

    public void setNavigationCallback(NavigationCallback callback) {
        this.navigationCallback = callback;
    }

    public void setQuitCallback(QuitCallback callback) {
        this.quitCallback = callback;
    }

    public void registerKeyBindings(EventLoop eventLoop, List<Disposable> subscriptions) {
        List<KeyBinding> keyBindings = getKeyBindings();

        keyBindings.forEach(binding -> {
            Disposable subscription = eventLoop.keyEvents()
                    .filter(keyEvent -> keyEvent.isKey(binding.key()))
                    .distinct()
                    .sample(DEBOUNCE_DURATION)
                    .subscribe(binding.action());

            subscriptions.add(subscription);
        });
    }

    public List<KeyBinding> getKeyBindings() {
        return Stream.concat(getCustomKeyBindings().stream(), getDefaultKeyBindings().stream()).toList();
    }

    protected abstract List<KeyBinding> getCustomKeyBindings();

    private List<KeyBinding> getDefaultKeyBindings() {
        return List.of(
                KeyBinding.create(KeyEvent.Key.q, "Quit", keyEvent -> quitCallback.quit())
        );
    }
}

