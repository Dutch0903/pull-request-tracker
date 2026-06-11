package com.pullrequesttracker.presentation.cli.view.token.component;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.presentation.cli.view.token.TokenManagerState;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.ListElement;
import org.springframework.stereotype.Component;

import java.util.List;

import static dev.tamboui.toolkit.Toolkit.list;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.spacer;
import static dev.tamboui.toolkit.Toolkit.text;

@Component
public class TokenList {
    private final TokenManagerState state;

    private final ListElement<?> listElement = list().highlightColor(Color.CYAN).highlightSymbol("> ").autoScroll();

    public TokenList(TokenManagerState state) {
        this.state = state;
    }

    public TokenDto getSelectedToken() {
        List<TokenDto> tokens = state.get(TokenManagerState.TOKENS).getOrElse(List.of());
        if (tokens.isEmpty())
            return null;
        int index = listElement.selected();
        return index < tokens.size() ? tokens.get(index) : null;
    }

    public Element render() {
        List<TokenDto> tokens = state.get(TokenManagerState.TOKENS).getOrElse(List.of());
        if (tokens.isEmpty()) {
            return panel(text("No tokens configured. Press c to create one.").dim()).fill().focusable()
                    .focusedBorderColor(Color.CYAN);
        }
        Element content = listElement.data(tokens, t -> row(text("%-10s".formatted(t.platform())).dim(), text(t.name()),
                spacer(), text("@" + t.username()).dim()));
        return panel(content).fill().focusable().focusedBorderColor(Color.CYAN)
                .onKeyEvent(event -> listElement.handleKeyEvent(event, true));
    }
}
