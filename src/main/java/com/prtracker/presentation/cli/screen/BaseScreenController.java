package com.prtracker.presentation.cli.screen;

import com.prtracker.presentation.cli.KeyBinding;
import io.github.kylekreuter.tamboui.spring.core.ScreenController;
import io.github.kylekreuter.tamboui.spring.core.TemplateModel;

import java.util.List;

public abstract class BaseScreenController implements ScreenController {
    protected abstract List<KeyBinding> getKeyBindings();

    @Override
    public void populate(TemplateModel templateModel) {
        templateModel.put("keyBindings", getFormattedKeyBindings());
    }

    private String getFormattedKeyBindings() {
        List<KeyBinding> keyBindings = getKeyBindings();

        StringBuilder formattedKeyBindings = new StringBuilder();

        for(int i = 0; i < keyBindings.size(); i++) {
            if (i > 0) {
                formattedKeyBindings.append(" | ");
            }

            KeyBinding keyBinding = keyBindings.get(i);

            formattedKeyBindings.append(keyBinding.toString());
        }

        return formattedKeyBindings.toString();
    }
}
