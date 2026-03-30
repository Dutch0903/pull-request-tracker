package com.prtracker.presentation.cli.dialog.form;

import dev.tamboui.toolkit.elements.FormFieldElement;

import java.util.List;

import static dev.tamboui.toolkit.Toolkit.formField;

public record SelectField(String id, String label, List<String> options, String initialValue) implements Field {
    @Override
    public FormFieldElement fieldElement() {
        return formField(label()).id(id());
    }
}
