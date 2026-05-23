package com.prtracker.app.cli.view.dashboard;

import com.prtracker.app.cli.dialog.DialogManager;
import com.prtracker.app.cli.navigation.View;
import com.prtracker.app.cli.navigation.ViewComponent;
import com.prtracker.app.cli.navigation.ViewName;
import com.prtracker.coderepository.application.query.CodeRepositoryProjection;
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
public class DashboardView extends View {
    private final DashboardController controller;
    private final DashboardState state;

    public DashboardView(DialogManager dialogManager, DashboardController controller, DashboardState state, DashboardKeyHandler keyHandler) {
        super(dialogManager, keyHandler);

        this.controller = controller;
        this.state = state;

        this.controller.loadRecentRepositories();
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    @Override
    protected Element renderBody() {
        ListElement<?> list = list().highlightColor(Color.CYAN).highlightSymbol("> ");
        List<CodeRepositoryProjection> repos = state.getRecentRepositories();
        for (CodeRepositoryProjection repo : repos) {
            list.add(repo.owner() + "/" + repo.name());
        }

        return list;
    }
}
