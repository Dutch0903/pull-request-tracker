package com.prtracker.infrastructure.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileStorage {
    private static final String DIRECTORY = "data";

    private final ObjectMapper objectmapper;

    public <T> List<T> load(String file, Class<T> expectedClass) throws JacksonException {
        Path path = Path.of(DIRECTORY, file);

        if (Files.notExists(path)) {
            return List.of();
        }

        CollectionType listType = objectmapper.getTypeFactory()
                .constructCollectionType(List.class, expectedClass);

        return objectmapper.readValue(path, listType);
    }

    public <T> void save(String file, Collection<T> data) throws IOException, JacksonException {
        Path directory = Path.of(DIRECTORY);

        if (Files.notExists(directory)) {
            Files.createDirectory(directory);
        }

        objectmapper.writeValue(directory.resolve(file), data);
    }
}
