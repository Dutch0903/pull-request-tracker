package com.prtracker.presentation.cli.dialog.form;

import com.prtracker.presentation.cli.dialog.Dialog;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.elements.DialogElement;
import dev.tamboui.toolkit.elements.FormFieldElement;
import dev.tamboui.widgets.form.FormState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dev.tamboui.toolkit.Toolkit.dialog;
import static dev.tamboui.toolkit.Toolkit.text;

public class FormDialog implements Dialog {
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
        return dialog(configuration.title(), buildElements()).onConfirm(this::submit).onCancel(closeDialog)
                .width(Math.max(50, configuration.description().length()));
    }

    private void submit() {
        Map<String, String> values = new HashMap<>();

        configuration.fields().forEach(field -> {
            String id = field.id();

            String value = switch (field) {
                case TextField f -> state.textValue(f.id());
                case SelectField f -> state.selectValue(f.id());
            };

            values.put(id, value);
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
            FormFieldElement element = field.fieldElement();
            element.onSubmit(this::submit);

            switch (field) {
                case TextField f -> element.state(state.textField(f.id()));
                case SelectField f -> element.state(state.selectField(f.id()));
            }

            elements.add(element);
        });

        if (errorMessage != null) {
            elements.add(text(errorMessage).red());
        }

        elements.add(text("[Enter] Confirm  [Esc] Cancel").dim());

        return elements.toArray(new Element[0]);
    }

    private void buildFormState() {
        FormState.Builder builder = FormState.builder();

        configuration.fields().forEach(field -> {
            switch (field) {
                case TextField f -> builder.textField(f.id(), f.initialValue());
                case SelectField f -> builder.selectField(f.id(), f.options(), f.options().indexOf(f.initialValue()));
            }
        });

        this.state = builder.build();
    }
}
