package com.pullrequesttracker.presentation.cli.component;

import dev.tamboui.layout.Padding;
import dev.tamboui.toolkit.element.Element;

import static dev.tamboui.toolkit.Toolkit.panel;

public class SectionPanel {
    public static Element sectionPanel(String title, Element... children) {
        return panel(title, children).fill().padding(Padding.symmetric(1, 2));
    }
}
