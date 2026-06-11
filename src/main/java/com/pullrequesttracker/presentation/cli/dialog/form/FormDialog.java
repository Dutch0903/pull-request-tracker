package com.pullrequesttracker.presentation.cli.dialog.form;

import com.pullrequesttracker.presentation.cli.dialog.Dialog;
import dev.tamboui.layout.Margin;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.elements.Column;
import dev.tamboui.toolkit.elements.DialogElement;
import dev.tamboui.toolkit.elements.FormFieldElement;
import dev.tamboui.widgets.form.FormState;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.tamboui.toolkit.Toolkit.*;

@Slf4j
public class FormDialog implements Dialog {
    private static final int MAX_WIDTH = 50;
    private static final int MAX_HEIGHT = 50;
    private static final int HORIZONTAL_PADDING = 1;
    // Dialog border = 2, column margin = HORIZONTAL_PADDING * 2
    private static final int WRAP_WIDTH = MAX_WIDTH - 2 - HORIZONTAL_PADDING * 2;

    private final FormDialogConfiguration configuration;
    private final FormDialogHandler handler;
    private final Runnable closeDialog;
    private FormState state;
    private String errorMessage;

    public FormDialog(FormDialogConfiguration configuration, FormDialogHandler handler, Runnable closeDialog) {
        this.configuration = configuration;
        this.handler = handler;
        this.closeDialog = closeDialog;
        this.buildFormState();
    }

    @Override
    public DialogElement render() {
        DialogElement dialog = dialog(configuration.title(),
                column(buildElements()).margin(Margin.horizontal(HORIZONTAL_PADDING))).onConfirm(this::submit)
                .onCancel(closeDialog);

        Size size = dialog.preferredSize(MAX_WIDTH, MAX_HEIGHT, null);

        dialog.width(MAX_WIDTH).length(size.height());

        return dialog;
    }

    private void submit() {
        Map<String, String> values = new HashMap<>();

        configuration.fields().forEach(field -> {
            switch (field) {
                case TextField f -> values.put(f.id(), state.textValue(f.id()));
                case SelectField f -> values.put(f.id(), state.selectValue(f.id()));
                case ReadOnlyField ignored -> {
                }
            }
        });

        try {
            handler.onSubmit(values);
        } catch (RuntimeException e) {
            this.errorMessage = e.getMessage();
            return;
        }

        closeDialog.run();
    }

    private Element[] buildElements() {
        List<Element> elements = new ArrayList<>();
        elements.add(text(configuration.description()));

        configuration.fields().forEach(field -> {
            switch (field) {
                case ReadOnlyField f -> elements.add(text(f.label() + ": " + f.initialValue()).dim());
                case TextField f -> {
                    FormFieldElement element = f.fieldElement();
                    element.onSubmit(this::submit);
                    element.state(state.textField(f.id()));
                    elements.add(element);
                }
                case SelectField f -> {
                    FormFieldElement element = f.fieldElement();
                    element.onSubmit(this::submit);
                    element.state(state.selectField(f.id()));
                    elements.add(element);
                }
            }
        });

        if (errorMessage != null) {
            elements.add(buildErrorElements(errorMessage));
        }

        elements.add(text("[Enter] Confirm  [Esc] Cancel").dim());

        return elements.toArray(new Element[0]);
    }

    private void buildFormState() {
        FormState.Builder builder = FormState.builder();

        configuration.fields().forEach(field -> {
            switch (field) {
                case ReadOnlyField ignored -> {
                }
                case TextField f -> builder.textField(f.id(), f.initialValue());
                case SelectField f -> builder.selectField(f.id(), f.options(), f.options().indexOf(f.initialValue()));
            }
        });

        this.state = builder.build();
    }

    private static Column buildErrorElements(String message) {
        List<Element> lines = new ArrayList<>();
        String remaining = message;
        while (remaining.length() > WRAP_WIDTH) {
            int breakAt = remaining.lastIndexOf(' ', WRAP_WIDTH);
            if (breakAt <= 0) {
                breakAt = WRAP_WIDTH;
            }
            lines.add(text(remaining.substring(0, breakAt)).red());
            remaining = remaining.substring(breakAt).stripLeading();
        }

        lines.add(text(remaining).red());

        // margin(vertical 1) adds 1 row top + 1 row bottom; length must account for
        // those
        // so the parent layout allocates enough space before the margin is applied
        return column(lines.toArray(new Element[0])).margin(Margin.vertical(1)).length(lines.size() + 2);
    }
}
