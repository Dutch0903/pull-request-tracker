package com.prtracker.presentation.cli.dialog.form;

import dev.tamboui.toolkit.elements.FormFieldElement;

public sealed interface Field permits SelectField, TextField {
    String id();

    String label();

    String initialValue();

    FormFieldElement fieldElement();
}
