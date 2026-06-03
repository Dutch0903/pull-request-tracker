package com.pullrequesttracker.presentation.cli.view.dashboard;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.elements.ListElement;

import java.util.List;

import static dev.tamboui.toolkit.Toolkit.list;

@ViewComponent(name = ViewName.DASHBOARD, isStartView = true)
public class DashboardView extends View {
    private final DashboardController controller;
    private final DashboardState state;

    public DashboardView(DialogManager dialogManager, DashboardController controller, DashboardState state,
            DashboardKeyHandler keyHandler) {
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
        ListElement<?> listEl = list().highlightColor(Color.CYAN).highlightSymbol("> ");
        List<CodeRepositoryDto> repos = state.getRecentRepositories();
        for (CodeRepositoryDto repo : repos) {
            listEl.add(repo.owner() + "/" + repo.name());
        }
        return listEl;
    }
}
