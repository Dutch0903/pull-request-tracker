package com.pullrequesttracker.presentation.cli.navigation;

import dev.tamboui.toolkit.element.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class ViewStack {
    private final Deque<Element> stack = new ArrayDeque<>();

    public void push(Element view) {
        stack.push(view);
    }

    public void pop() {
        stack.pop();
    }

    public Element peek() {
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }
}
