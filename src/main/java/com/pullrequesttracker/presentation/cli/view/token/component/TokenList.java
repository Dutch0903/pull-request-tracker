package com.pullrequesttracker.presentation.cli.view.token.component;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.presentation.cli.view.token.TokenManagerState;
import dev.tamboui.layout.Margin;
import dev.tamboui.layout.Padding;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.ListElement;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static dev.tamboui.toolkit.Toolkit.column;
import static dev.tamboui.toolkit.Toolkit.list;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.text;

@Component
public class TokenList {
    private final TokenManagerState state;

    private static final int COL_PLATFORM = 10;
    private static final int COL_NAME = 20;
    private static final int COL_USERNAME = 25;
    private static final int COL_EXPIRATION_DATE = 10;

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

        // left margin = 2 to align with the list's "> " / " " highlight prefix
        Element header = row(text(("%-" + COL_PLATFORM + "s").formatted("PLATFORM")).dim(), text("NAME").dim().fill(),
                text(("%-" + COL_USERNAME + "s").formatted("USERNAME")).dim(),
                text(("%-" + COL_EXPIRATION_DATE + "s").formatted("EXPIRES")).dim()).spacing(2)
                .margin(new Margin(0, 0, 0, 2)).length(1);

        Element list = listElement
                .data(tokens,
                        t -> row(text(("%-" + COL_PLATFORM + "s").formatted(t.platform())).dim(), text(t.name()).fill(),
                                text(formatUsername(t.username())).dim(), expiryElement(t.expirationDate()))
                                .spacing(2));

        return panel(column(header, list)).fill().focusable().focusedBorderColor(Color.CYAN)
                .onKeyEvent(event -> listElement.handleKeyEvent(event, true)).padding(Padding.symmetric(1, 2));
    }

    private static String formatUsername(String username) {
        return username.length() <= COL_USERNAME
                ? ("%-" + COL_USERNAME + "s").formatted(username)
                : username.substring(0, COL_USERNAME);
    }

    private Element expiryElement(String expirationDate) {
        Instant expiry = Instant.parse(expirationDate);
        Duration remaining = Duration.between(Instant.now(), expiry);
        String label = expiry.toString().substring(0, 10);
        if (remaining.isNegative()) {
            return text(label).red();
        }
        if (remaining.toDays() < 10) {
            return text(label).yellow();
        }
        return text(label).dim();
    }
}
