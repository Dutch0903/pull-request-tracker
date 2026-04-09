package com.prtracker.presentation.cli.view.token;

import com.prtracker.presentation.cli.ViewComponent;
import com.prtracker.presentation.cli.ViewName;
import com.prtracker.presentation.cli.dialog.DialogManager;
import com.prtracker.presentation.cli.view.View;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import static dev.tamboui.toolkit.Toolkit.*;

@ViewComponent(name = ViewName.TOKENS)
public class TokenManagerView extends View {
    private final TokenManagerKeyHandler keyHandler;
    private final TokenManagerState state;

    public TokenManagerView(DialogManager dialogManager, TokenManagerKeyHandler keyHandler, TokenManagerState state) {
        super(dialogManager);

        this.keyHandler = keyHandler;
        this.state = state;
    }

    @Override
    protected Element renderBody() {
        return row(this.renderTokenList());
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
                list().data(state.getTokens(), tokenProjection -> text(tokenProjection.name()))
                        .highlightColor(Color.CYAN).highlightSymbol("> ").autoScroll().scrollbar()
                        .onKeyEvent(keyHandler::handle).selected(state.getSelectedIndex())
                        .scrollbarThumbColor(Color.CYAN)))
                .fill().borderless();
    }
}
