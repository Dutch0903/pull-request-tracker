package com.pullrequesttracker.presentation.cli.view.token;

import com.pullrequesttracker.infrastructure.config.ViewRefreshProperties;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import com.pullrequesttracker.presentation.cli.view.token.component.TokenList;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.tui.event.KeyEvent;
import dev.tamboui.toolkit.event.EventResult;

import static dev.tamboui.toolkit.Toolkit.row;

@ViewComponent(name = ViewName.TOKENS)
public class TokenManagerView extends View {
    private final TokenManagerController controller;
    private final TokenList tokenList;

    public TokenManagerView(DialogManager dialogManager, TokenManagerKeyHandler keyHandler,
            TokenManagerController controller, TokenList tokenList, ViewRefreshProperties viewRefreshProperties) {
        super(dialogManager, keyHandler, viewRefreshProperties);
        this.controller = controller;
        this.tokenList = tokenList;
    }

    @Override
    protected void refreshState() {
        controller.loadTokens();
    }

    @Override
    protected Element renderBody() {
        return row(tokenList.render());
    }

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        if (renderedDialog != null && dialogManager.isDialogOpen()) {
            return renderedDialog.handleKeyEvent(event, true);
        }
        return super.handleKeyEvent(event, focused);
    }
}
