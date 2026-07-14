package com.pullrequesttracker.presentation.cli.navigation;

import org.springframework.stereotype.Component;

import java.util.ArrayDeque;
import java.util.Deque;

@Component
public class ViewStack {
    private final Deque<View> stack = new ArrayDeque<>();

    public void push(View view) {
        stack.push(view);
    }

    public void pop() {
        stack.pop();
    }

    public View peek() {
        return stack.peek();
    }

    public int size() {
        return stack.size();
    }
}
