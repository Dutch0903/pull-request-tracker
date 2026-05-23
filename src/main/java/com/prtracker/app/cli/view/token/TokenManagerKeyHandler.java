package com.prtracker.app.cli.view.token;

import com.prtracker.app.cli.action.KeyHandler;
import com.prtracker.app.cli.view.token.action.NavigateToDashboard;
import com.prtracker.app.cli.view.token.action.OpenCreateTokenDialog;
import com.prtracker.app.cli.view.token.action.OpenDeleteTokenDialog;
import com.prtracker.app.cli.view.token.action.OpenUpdateTokenDialog;
import com.prtracker.app.cli.view.token.dialog.DeleteTokenDialogAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenManagerKeyHandler extends KeyHandler {
    protected TokenManagerKeyHandler(
            NavigateToDashboard navigateToDashboard,
            OpenCreateTokenDialog openCreateTokenDialog,
            OpenUpdateTokenDialog openUpdateTokenDialog,
            OpenDeleteTokenDialog openDeleteTokenDialog
    ) {
        super(List.of(navigateToDashboard, openCreateTokenDialog, openUpdateTokenDialog, openDeleteTokenDialog));
    }

//    public EventResult handle(KeyEvent event) {
//        if (event.isCharIgnoreCase('d')) {
//            navigationEventPublisher.navigateTo(ViewName.DASHBOARD);
//            return EventResult.HANDLED;
//        }
//
//        if (event.isCharIgnoreCase('c')) {
//            createTokenDialogAction.open();
//            return EventResult.HANDLED;
//        }
//
//        if (event.isCharIgnoreCase('u')) {
//            updateTokenDialogAction.open();
//            return EventResult.HANDLED;
//        }
//
//        if (event.isDeleteBackward()) {
//            deleteTokenDialogAction.open();
//            return EventResult.HANDLED;
//        }
//
//        if (event.isDown()) {
//            state.selectNext();
//            return EventResult.HANDLED;
//        }
//
//        if (event.isUp()) {
//            state.selectPrevious();
//            return EventResult.HANDLED;
//        }
//
//        return EventResult.UNHANDLED;
//    }
}
