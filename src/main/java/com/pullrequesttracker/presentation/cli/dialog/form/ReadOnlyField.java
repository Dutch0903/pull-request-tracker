package com.pullrequesttracker.presentation.cli.dialog.form;

import dev.tamboui.toolkit.elements.FormFieldElement;

import static dev.tamboui.toolkit.Toolkit.formField;

public record ReadOnlyField(String id, String label, String initialValue) implements Field {
    @Override
    public FormFieldElement fieldElement() {
        return formField(label()).id(id());
    }
}
