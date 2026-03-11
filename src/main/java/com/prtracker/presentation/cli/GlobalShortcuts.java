package com.prtracker.presentation.cli;

import io.github.kylekreuter.tamboui.spring.annotation.OnKey;
import org.springframework.stereotype.Component;

@Component
public class GlobalShortcuts {

    @OnKey("ctrl+q")
    public void onQuit() {
        // fires on any screen
        System.exit(0);
    }
}