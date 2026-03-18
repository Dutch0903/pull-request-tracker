package com.prtracker.presentation.cli;

import com.prtracker.presentation.cli.event.NavigationEvent;
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
        String startViewName = this.viewRegistry.getStartViewName();

        viewStack.push(this.viewRegistry.getView(startViewName));
    }

    public void push(String viewName) {
        Element view = this.viewRegistry.getView(viewName);
        viewStack.push(view);
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
