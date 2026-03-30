package com.prtracker.presentation.cli.view.token;

import com.prtracker.presentation.cli.ViewComponent;
import com.prtracker.presentation.cli.ViewName;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.view.View;
import dev.tamboui.layout.Rect;
import dev.tamboui.style.Color;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.elements.DialogElement;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;

import static dev.tamboui.toolkit.Toolkit.*;

@RequiredArgsConstructor
@ViewComponent(name = ViewName.TOKENS)
public class TokenManangerView extends View {
    private final DialogManager dialogManager;
    private final TokenManagerKeyHandler keyHandler;
    private final TokenManagerController controller;

    private DialogElement renderedDialog;

    @Override
    protected Element renderBody() {
        return row(this.renderTokenList());
    }

    @Override
    protected void renderOverlay(Frame frame, Rect area, RenderContext context) {
        if (!dialogManager.isDialogOpen()) {
            renderedDialog = null;
            return;
        }

        renderedDialog = dialogManager.getCurrentDialog().render();
        renderedDialog.render(frame, area, context);
    }

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        if (renderedDialog != null && dialogManager.isDialogOpen()) {
            return renderedDialog.handleKeyEvent(event, true);
        }

        return keyHandler.handle(event);
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    private Element renderTokenList() {
        return panel(column(text("Tokens"),
                list().data(controller.getTokens(), tokenProjection -> text(tokenProjection.name()))
                        .highlightColor(Color.CYAN).highlightSymbol("> ").autoScroll().scrollbar()
                        .onKeyEvent(keyHandler::handle).selected(controller.getSelectedIndex())
                        .scrollbarThumbColor(Color.CYAN)))
                .fill().borderless();
    }
}
