package com.pullrequesttracker.presentation.cli.navigation;

import dev.tamboui.toolkit.element.Element;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ViewManager {
    private final ViewRegistry viewRegistry;
    private final ViewStack viewStack;

    public ViewManager(ViewRegistry viewRegistry, ViewStack viewStack) {
        this.viewRegistry = viewRegistry;
        this.viewStack = viewStack;
        viewStack.push(viewRegistry.getView(viewRegistry.getStartViewName()));
    }

    public void push(String viewName) {
        viewStack.push(viewRegistry.getView(viewName));
    }

    public void pop() {
        if (viewStack.size() > 1) {
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

    @EventListener
    public void onNavigationPopEvent(NavigationPopEvent event) {
        pop();
    }
}
