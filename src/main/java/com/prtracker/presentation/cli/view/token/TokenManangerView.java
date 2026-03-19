package com.prtracker.presentation.cli.view.token;

import com.prtracker.presentation.cli.ViewComponent;
import com.prtracker.presentation.cli.ViewName;
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
    private final TokenManagerKeyHandler keyHandler;
    private final TokenManagerController controller;

    private DialogElement currentDialog;

    @Override
    protected Element renderBody() {
        return row(this.renderTokenList());
    }

    @Override
    protected void renderOverlay(Frame frame, Rect area, RenderContext context) {
        if (!controller.hasDialog()) {
            currentDialog = null;
            return;
        }

        switch (controller.getCurrentDialog()) {
            case CREATE -> this.renderCreateDialog(frame, area, context);
            case UPDATE -> this.renderUpdateDialog(frame, area, context);
        }
    }

    @Override
    public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
        if (currentDialog != null && controller.hasDialog()) {
            return currentDialog.handleKeyEvent(event, true);
        }

        return keyHandler.handle(event);
    }

    @Override
    public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
        return Size.UNKNOWN;
    }

    private Element renderTokenList() {
        return panel(text("Tokens")).fill().borderless();
    }

    private void renderCreateDialog(Frame frame, Rect area, RenderContext context) {
        currentDialog = this.createDialog("Create token", controller::confirmDialog);

        currentDialog.render(frame, area, context);
    }

    private void renderUpdateDialog(Frame frame, Rect area, RenderContext context) {
        currentDialog = this.createDialog("Update token", controller::confirmDialog);

        currentDialog.render(frame, area, context);
    }

    private DialogElement createDialog(String title, Runnable onConfirm) {
        String dialogMessage = controller.getDialogMessage();
        return dialog(title, text(dialogMessage),
                formField("Name", controller.getNameInputState()).id("name").labelWidth(5).rounded()
                        .borderColor(Color.GRAY).focusedBorderColor(Color.CYAN).onSubmit(onConfirm),
                formField("Token", controller.getTokenInputState()).id("token").labelWidth(5).rounded()
                        .borderColor(Color.GRAY).focusedBorderColor(Color.CYAN).onSubmit(onConfirm),
                text("[Enter] Confirm  [Esc] Cancel").dim()).rounded().width(Math.max(50, (dialogMessage.length() + 4)))
                .onConfirm(onConfirm).onCancel(controller::dismissDialog);
    }
}
