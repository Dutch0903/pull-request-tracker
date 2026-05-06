package com.prtracker.shared.cli.dialog;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DialogFactory {
    private final Map<DialogType, DialogCreator<? extends DialogConfiguration>> registry;

    public DialogFactory(List<DialogCreator<? extends DialogConfiguration>> creators) {
        this.registry = creators.stream().collect(Collectors.toMap(DialogCreator::getDialogType, c -> c));
    }

    @SuppressWarnings("unchecked")
    public <T extends DialogConfiguration> Dialog create(DialogType type, T configuration, DialogHandler handler,
            Runnable closeDialog) {
        DialogCreator<T> creator = (DialogCreator<T>) registry.get(type);

        if (creator == null) {
            throw new IllegalArgumentException("No dialog registered for type: " + type);
        }

        return creator.create(configuration, handler, closeDialog);
    }
}
