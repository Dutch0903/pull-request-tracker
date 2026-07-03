package com.pullrequesttracker.presentation.cli.navigation;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class NavigationEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public NavigationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void navigateTo(String viewName) {
        eventPublisher.publishEvent(new NavigationEvent(viewName));
    }

    public void navigateBack() {
        eventPublisher.publishEvent(new NavigationPopEvent());
    }
}
