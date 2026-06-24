package com.pullrequesttracker.presentation.cli.view.repository.component;

import com.pullrequesttracker.application.dto.CodeRepositoryDto;
import com.pullrequesttracker.presentation.cli.view.repository.RepositoryListState;
import dev.tamboui.layout.Padding;
import dev.tamboui.style.Color;

import java.util.Collections;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.ListElement;
import org.springframework.stereotype.Component;

import static dev.tamboui.toolkit.Toolkit.list;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.spacer;
import static dev.tamboui.toolkit.Toolkit.text;

@Component
public class RepositoryList {
    private final RepositoryListState state;

    private final ListElement<?> listElement = list().highlightColor(Color.LIGHT_GREEN).highlightSymbol(">> ")
            .autoScroll();

    public RepositoryList(RepositoryListState state) {
        this.state = state;
    }

    public CodeRepositoryDto getSelectedRepository() {
        var repos = state.get(RepositoryListState.REPOSITORIES).getOrElse(Collections.emptyList());
        if (repos.isEmpty())
            return null;
        int index = listElement.selected();
        return index < repos.size() ? repos.get(index) : null;
    }

    public Element render() {
        var repos = state.get(RepositoryListState.REPOSITORIES).getOrElse(Collections.emptyList());
        Element content = repos.isEmpty()
                ? text("No repositories configured.").dim()
                : listElement.data(repos, repo -> row(text(repo.owner() + "/" + repo.name()), spacer()));

        return panel("Repositories", content).id("repository-list").focusable().focusedBorderColor(Color.LIGHT_GREEN)
                .rounded().onKeyEvent(event -> listElement.handleKeyEvent(event, true))
                .padding(Padding.symmetric(1, 2));
    }
}
