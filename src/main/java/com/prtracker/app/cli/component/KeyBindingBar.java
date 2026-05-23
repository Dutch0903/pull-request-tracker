package com.prtracker.app.cli.component;

import com.prtracker.app.cli.action.KeyBinding;
import dev.tamboui.toolkit.element.Element;

import java.util.List;

import static dev.tamboui.toolkit.Toolkit.row;
import static dev.tamboui.toolkit.Toolkit.text;

public class KeyBindingBar {
    private final List<KeyBinding> keyBindings;

    public KeyBindingBar(List<KeyBinding> keyBindings) {
        this.keyBindings = keyBindings;
    }

    public Element render() {
        var items = keyBindings.stream().map(keyBinding -> text(keyBinding.toString())).toArray(Element[]::new);

        return row(items);
    }
}
