package com.prtracker.presentation.cli.screen;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ScreenFactory {
    private final Map<ScreenType, BaseScreen> screens;

    public ScreenFactory(List<BaseScreen> screenBeans) {
        this.screens = new HashMap<>();
        for(BaseScreen screen: screenBeans) {
            screens.put(screen.getType(), screen);
        }
    }

    public BaseScreen getScreen(ScreenType type) {
        BaseScreen screen = screens.get(type);

        if (screen == null) {
            throw new IllegalArgumentException("Unknown screen type: " + type);
        }

        return screen;
    }
}
