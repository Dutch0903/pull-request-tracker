package com.prtracker.infrastructure.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FileStorageTest {
    @TempDir
    private Path tempDir;

    private FileStorage fileStorage;

    @BeforeEach
    public void setup() {
        fileStorage = new FileStorage(tempDir.toString(), new ObjectMapper());
    }

    @Test
    void load_whenFileDoesNotExists_shouldReturnEmptyList() {
        String nonExistentFile = "non-existent-file";
        List<Dto> list = fileStorage.load(nonExistentFile, Dto.class);

        assertFalse(Files.exists(tempDir.resolve(nonExistentFile)));
        assertEquals(List.of(), list);
    }

    @Test
    void save_thenLoad_shouldReturnSavedData() throws IOException {
        String file = "dtos.json";
        List<Dto> list = List.of(new Dto("name"));

        fileStorage.save(file, list);
        List<Dto> result = fileStorage.load(file, Dto.class);

        assertEquals(list, result);
    }

    @Test
    void save_whenFileDoesNotExists_shouldCreateFile() throws IOException {
        String file = "dtos.json";
        List<Dto> list = List.of(new Dto("name"));
        assertFalse(Files.exists(tempDir.resolve(file)));

        fileStorage.save(file, list);

        assertTrue(Files.exists(tempDir.resolve(file)));
    }

    record Dto(String name) {
    }
}
