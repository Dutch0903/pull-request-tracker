package com.prtracker.presentation.cli;

import com.prtracker.presentation.cli.screen.ScreenType;

@FunctionalInterface
public interface NavigationCallback {
    void navigateTo(ScreenType screenType);
}
