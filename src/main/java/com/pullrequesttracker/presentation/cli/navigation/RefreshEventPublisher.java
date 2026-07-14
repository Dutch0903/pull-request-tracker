package com.pullrequesttracker.presentation.cli.navigation;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class RefreshEventPublisher {
    private final ApplicationEventPublisher eventPublisher;

    public RefreshEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void refresh() {
        eventPublisher.publishEvent(new RefreshEvent());
    }
}
