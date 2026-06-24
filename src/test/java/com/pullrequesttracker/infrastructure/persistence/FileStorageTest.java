package com.pullrequesttracker.infrastructure.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FileStorageTest {
    @TempDir
    private Path tempDir;

    private FileStorage fileStorage;

    @BeforeEach
    void setup() {
        fileStorage = new FileStorage(tempDir.toString(), new ObjectMapper());
    }

    @Test
    void load_whenFileDoesNotExist_shouldReturnEmptyList() {
        String file = "non-existent.json";
        assertEquals(Collections.emptyList(), fileStorage.load(file, Dto.class));
        assertFalse(Files.exists(tempDir.resolve(file)));
    }

    @Test
    void save_thenLoad_shouldReturnSavedData() throws IOException {
        String file = "dtos.json";
        List<Dto> list = List.of(new Dto("name"));

        fileStorage.save(file, list);

        assertEquals(list, fileStorage.load(file, Dto.class));
    }

    @Test
    void save_whenFileDoesNotExist_shouldCreateFile() throws IOException {
        String file = "dtos.json";
        assertFalse(Files.exists(tempDir.resolve(file)));

        fileStorage.save(file, List.of(new Dto("name")));

        assertTrue(Files.exists(tempDir.resolve(file)));
    }

    @Test
    void save_whenDirectoryDoesNotExist_shouldCreateDirectory() throws IOException {
        Path dir = tempDir.resolve(UUID.randomUUID().toString());
        fileStorage = new FileStorage(dir.toString(), new ObjectMapper());

        fileStorage.save("dtos.json", List.of(new Dto("name")));

        assertTrue(Files.exists(dir));
    }

    record Dto(String name) {
    }
}
