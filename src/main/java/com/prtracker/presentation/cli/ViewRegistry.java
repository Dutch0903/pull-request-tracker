package com.prtracker.presentation.cli;

import dev.tamboui.toolkit.element.Element;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ViewRegistry {
    private final Map<String, Element> viewCache = new HashMap<>();
    private String startViewName;

    public ViewRegistry(List<Element> views) {
        registerViews(views);
    }

    private void registerViews(List<Element> views) {
        for (Element view : views) {
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

    public Element getView(String name) {
        Element view = viewCache.get(name);

        if (view == null) {
            throw new IllegalArgumentException("View not found: " + name);
        }

        return  view;
    }

    public String getStartViewName() {
        return startViewName;
    }
}
