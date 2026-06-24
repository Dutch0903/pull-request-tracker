package com.pullrequesttracker.infrastructure.persistence;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.type.CollectionType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class FileStorage {
    private final String directory;
    private final ObjectMapper objectMapper;

    public FileStorage(@Value("${storage.directory:data}") String directory, ObjectMapper objectMapper) {
        this.directory = directory;
        this.objectMapper = objectMapper;
    }

    public <T> List<T> load(String file, Class<T> expectedClass) throws JacksonException {
        Path path = Path.of(directory, file);

        if (Files.notExists(path)) {
            return Collections.emptyList();
        }

        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, expectedClass);
        return objectMapper.readValue(path, listType);
    }

    public <T> void save(String file, Collection<T> data) throws IOException, JacksonException {
        Path path = Path.of(directory);

        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }

        objectMapper.writeValue(path.resolve(file), data);
    }
}
