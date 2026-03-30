package com.prtracker.presentation.cli.dialog.form;

import dev.tamboui.style.Color;
import dev.tamboui.toolkit.elements.FormFieldElement;

import static dev.tamboui.toolkit.Toolkit.formField;

public record TextField(String id, String label, boolean masked, String initialValue) implements Field {
    @Override
    public FormFieldElement fieldElement() {
        FormFieldElement element = formField(label()).id(id()).labelWidth(5).rounded().borderColor(Color.GRAY)
                .focusedBorderColor(Color.CYAN);

        if (masked()) {
            element.masked();
        }

        return element;
    }
}
