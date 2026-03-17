package com.prtracker.presentation.cli.view.token;

import com.prtracker.presentation.cli.View;
import com.prtracker.presentation.cli.ViewComponent;
import dev.tamboui.layout.Rect;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;

import static dev.tamboui.toolkit.Toolkit.*;

@RequiredArgsConstructor
@ViewComponent(name = View.TOKENS)
public class TokenListView implements Element {
    private final TokenListKeyHandler keyHandler;

    @Override
    public void render(Frame frame, Rect area, RenderContext context) {
        Element ui = dock().center(row(
                this.renderTokenList()
        ));

        ui.render(frame, area, context);
    }

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        return keyHandler.handle(event);
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    private Element renderTokenList() {
        return panel(
                text("Tokens")
        ).fill();
    }
}
