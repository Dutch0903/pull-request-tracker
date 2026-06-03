package com.pullrequesttracker.presentation.cli.view.repository.component;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.Panel;

import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.text;

public class RepositoryDetailPanel {
    private final CodeRepositoryDto projection;

    public RepositoryDetailPanel(CodeRepositoryDto projection) {
        this.projection = projection;
    }

    public Panel render() {
        String title = projection != null ? projection.owner() + "/" + projection.name() : "Details";

        Element body = projection != null
                ? text("Loading stats...").dim()
                : text("Select a repository to view details.").dim();

        return panel(title, body)
                .id("repository-detail-panel")
                .focusable()
                .focusedBorderColor(Color.LIGHT_GREEN)
                .rounded();
    }
}
