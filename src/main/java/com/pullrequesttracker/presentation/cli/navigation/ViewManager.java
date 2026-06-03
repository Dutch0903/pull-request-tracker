package com.pullrequesttracker.presentation.cli.navigation;

import dev.tamboui.toolkit.element.Element;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class ViewManager {
    private final ViewRegistry viewRegistry;
    private final Deque<Element> viewStack = new ArrayDeque<>();

    public ViewManager(ViewRegistry viewRegistry) {
        this.viewRegistry = viewRegistry;
        viewStack.push(viewRegistry.getView(viewRegistry.getStartViewName()));
    }

    public void push(String viewName) {
        viewStack.push(viewRegistry.getView(viewName));
    }

    public void pop() {
        if (!viewStack.isEmpty()) {
            viewStack.pop();
        }
    }

    public Element getCurrentView() {
        return viewStack.peek();
    }

    public void replace(String viewName) {
        pop();
        push(viewName);
    }

    @EventListener
    public void onNavigationEvent(NavigationEvent event) {
        push(event.viewName());
    }
}
