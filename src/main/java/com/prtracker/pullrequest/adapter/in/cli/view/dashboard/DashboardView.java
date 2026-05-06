package com.prtracker.pullrequest.adapter.in.cli.view.dashboard;

import com.prtracker.coderepository.application.query.CodeRepositoryProjection;
import com.prtracker.shared.cli.ViewName;
import com.prtracker.shared.cli.ViewComponent;
import dev.tamboui.layout.Rect;
import dev.tamboui.style.Color;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.elements.ListElement;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import java.util.List;

import static dev.tamboui.toolkit.Toolkit.*;
import static dev.tamboui.toolkit.Toolkit.dock;
import static dev.tamboui.toolkit.Toolkit.text;

@ViewComponent(name = ViewName.DASHBOARD, isStartView = true)
public class DashboardView implements Element {
    private final DashboardController controller;
    private final DashboardState state;
    private final DashboardKeyHandler keyHandler;

    public DashboardView(DashboardController controller, DashboardState state, DashboardKeyHandler keyHandler) {
        this.controller = controller;
        this.state = state;
        this.keyHandler = keyHandler;

        this.controller.loadRecentRepositories();
    }

    @Override
    public void render(Frame frame, Rect area, RenderContext context) {
        Element ui = dock().top(header()).center(content()).bottom(footer());

        ui.render(frame, area, context);
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        return keyHandler.handle(event);
    }

    private Element header() {
        return text("Header");
    }

    private Element content() {
        ListElement<?> list = list().highlightColor(Color.CYAN).highlightSymbol("> ");
        List<CodeRepositoryProjection> repos = state.getRecentRepositories();
        for (CodeRepositoryProjection repo : repos) {
            list.add(repo.owner() + "/" + repo.name());
        }

        return list;
    }

    private Element footer() {
        return text("Footer");
    }
}
