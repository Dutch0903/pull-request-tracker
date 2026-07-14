package com.pullrequesttracker.presentation.cli.navigation;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ViewManager {
    private final ViewRegistry viewRegistry;
    private final ViewStack viewStack;

    public ViewManager(ViewRegistry viewRegistry, ViewStack viewStack) {
        this.viewRegistry = viewRegistry;
        this.viewStack = viewStack;
        push(viewRegistry.getStartViewName());
    }

    public void push(String viewName) {
        viewStack.push(viewRegistry.getView(viewName));
        viewStack.peek().triggerRefresh();
    }

    public void pop() {
        if (viewStack.size() > 1) {
            viewStack.pop();
        }
    }

    public View getCurrentView() {
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

    @EventListener
    public void onRefreshEvent(RefreshEvent event) {
        viewStack.peek().triggerRefresh();
    }
}
