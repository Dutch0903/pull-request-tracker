package com.prtracker.presentation.cli.view;

import com.prtracker.presentation.cli.KeyBinding;
import org.springframework.shell.jline.tui.component.view.control.BoxView;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FooterView {
    public BoxView create(List<KeyBinding> keyBindings) {
        BoxView footer = new BoxView();

        String footerText = buildFooterText(keyBindings);

        footer.setDrawFunction((screen, rectangle) -> {
            screen.writerBuilder().build().text(footerText, rectangle.x() + 1, rectangle.y() + 1);
            return rectangle;
        });

        return footer;
    }

    private String buildFooterText(List<KeyBinding> keyBindings) {
        StringBuilder text = new StringBuilder();

        for (KeyBinding binding : keyBindings) {
            if (!text.isEmpty()) {
                text.append(" | ");
            }

            text.append(formatKeyBinding(binding));
        }

        return text.toString();
    }

    private String formatKeyBinding(KeyBinding binding) {
        String keyName = String.valueOf((char) binding.key());
        return keyName + ": " + binding.description();
    }
}
