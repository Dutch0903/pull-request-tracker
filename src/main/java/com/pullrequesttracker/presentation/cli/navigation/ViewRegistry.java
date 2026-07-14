package com.pullrequesttracker.presentation.cli.navigation;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ViewRegistry {
    private final Map<String, View> viewCache = new HashMap<>();
    private String startViewName;

    public ViewRegistry(List<View> views) {
        for (View view : views) {
            ViewComponent annotation = view.getClass().getAnnotation(ViewComponent.class);

            if (annotation == null) {
                continue;
            }

            viewCache.put(annotation.name(), view);

            if (annotation.isStartView()) {
                startViewName = annotation.name();
            }
        }
    }

    public View getView(String name) {
        View view = viewCache.get(name);

        if (view == null) {
            throw new IllegalArgumentException("View not found: " + name);
        }

        return view;
    }

    public String getStartViewName() {
        return startViewName;
    }
}
