package com.pullrequesttracker.presentation.cli.view.token;

import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import dev.tamboui.style.Color;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;

import static dev.tamboui.toolkit.Toolkit.column;
import static dev.tamboui.toolkit.Toolkit.list;
import static dev.tamboui.toolkit.Toolkit.panel;
import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.text;

@ViewComponent(name = ViewName.TOKENS)
public class TokenManagerView extends View {
    private final TokenManagerState state;

    public TokenManagerView(DialogManager dialogManager, TokenManagerKeyHandler keyHandler, TokenManagerState state) {
        super(dialogManager, keyHandler);
        this.state = state;
    }

    @Override
    protected Element renderBody() {
        return row(renderTokenList());
    }

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        if (renderedDialog != null && dialogManager.isDialogOpen()) {
            return renderedDialog.handleKeyEvent(event, true);
        }
        return super.handleKeyEvent(event, focused);
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    private Element renderTokenList() {
        return panel(column(text("Tokens"),
                list().data(state.getTokens(), t -> text(t.name()))
                        .highlightColor(Color.CYAN).highlightSymbol("> ").autoScroll().scrollbar()
                        .onKeyEvent(keyHandler::handle).selected(state.getSelectedIndex())
                        .scrollbarThumbColor(Color.CYAN)))
                .fill().borderless();
    }
}
