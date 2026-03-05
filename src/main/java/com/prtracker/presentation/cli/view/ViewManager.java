package com.prtracker.presentation.cli.view;

import com.prtracker.presentation.cli.NavigationCallback;
import com.prtracker.presentation.cli.screen.BaseScreen;
import com.prtracker.presentation.cli.screen.ScreenFactory;
import com.prtracker.presentation.cli.screen.ScreenType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.jline.tui.component.message.ShellMessageBuilder;
import org.springframework.shell.jline.tui.component.view.TerminalUI;
import org.springframework.shell.jline.tui.component.view.TerminalUIBuilder;
import org.springframework.shell.jline.tui.component.view.control.BoxView;
import org.springframework.shell.jline.tui.component.view.control.GridView;
import org.springframework.shell.jline.tui.component.view.control.View;
import org.springframework.shell.jline.tui.component.view.event.EventLoop;
import org.springframework.stereotype.Component;
import reactor.core.Disposable;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ViewManager implements NavigationCallback, QuitCallback {
    private final ScreenFactory screenFactory;
    private final FooterView footerView;
    private final TerminalUI terminalUI;
    private final List<Disposable> subscriptions = new ArrayList<>();

    public ViewManager(TerminalUIBuilder terminalUIBuilder, ScreenFactory screenFactory, FooterView footerView) {
        this.screenFactory = screenFactory;
        this.footerView = footerView;

        terminalUI = terminalUIBuilder.build();
    }

    public void start(ScreenType screenType) {
        navigateTo(screenType);

        terminalUI.run();
    }

    @Override
    public void navigateTo(ScreenType screenType) {
        BaseScreen screen = screenFactory.getScreen(screenType);
        screen.setNavigationCallback(this);
        screen.setQuitCallback(this);

        clearSubscriptions();

        EventLoop eventLoop = terminalUI.getEventLoop();
        screen.registerKeyBindings(eventLoop, subscriptions);

        GridView layout = createLayout(screen);
        terminalUI.setRoot(layout, true);
        terminalUI.redraw();
    }

    @Override
    public void quit() {
        clearSubscriptions();

        EventLoop eventLoop = terminalUI.getEventLoop();

        eventLoop.dispatch(ShellMessageBuilder.ofInterrupt());
    }

    private GridView createLayout(BaseScreen screen) {
        View content = screen.render();

        BoxView footer = footerView.create(screen.getKeyBindings());

        GridView layout = new GridView();
        layout.setRowSize(0, 2);
        layout.addItem(content, 0, 0, 1, 1, 0, 0);
        layout.addItem(footer, 1, 0, 1, 1, 0, 0);

        return layout;
    }

    private void clearSubscriptions() {
        subscriptions.forEach(Disposable::dispose);
        subscriptions.clear();
    }
}

