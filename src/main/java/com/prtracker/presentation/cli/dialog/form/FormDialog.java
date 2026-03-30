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

    public FormDialog(FormDialogConfiguration configuration, FormDialogHandler handler, Runnable closeDialog) {
        this.configuration = configuration;
        this.handler = handler;
        this.closeDialog = closeDialog;

        this.buildFormState();
    }

    @Override
    public DialogElement render() {
        return dialog(configuration.title(), buildElements()).onConfirm(this::submit).onCancel(closeDialog);
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

        handler.onSubmit(values);
        closeDialog.run();
    }

    private Element[] buildElements() {
        List<Element> elements = new ArrayList<>();
        elements.add(text(configuration.description()));

        configuration.fields().forEach(field -> {
            FormFieldElement element = field.fieldElement();
            element.onSubmit(this::submit);

            switch (field) {
                case TextField f -> element.state(state.textField(field.id()));
                case SelectField f -> element.state(state.selectField(field.id()));
            }

            elements.add(element);
        });

        elements.add(text("[Enter] Confirm  [Esc] Cancel").dim());

        return elements.toArray(new Element[0]);
    }

    private void buildFormState() {
        FormState.Builder builder = FormState.builder();

        configuration.fields().forEach(field -> {
            switch (field) {
                case TextField f -> builder.textField(f.id(), f.initialValue());
                case SelectField f -> builder.selectField(f.id(), f.options(), f.options().indexOf(f.id()));
            }
        });

        this.state = builder.build();
    }
}
