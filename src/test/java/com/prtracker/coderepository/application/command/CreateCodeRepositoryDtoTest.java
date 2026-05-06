package com.prtracker.coderepository.application.command;

import com.prtracker.coderepository.application.command.CreateCodeRepositoryDto;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class CreateCodeRepositoryDtoTest {
    @Test
    void construct_whenRepositoryReferenceIsNull_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CreateCodeRepositoryDto(null, null));

        assertEquals("Repository reference cannot be null or blank", exception.getMessage());
    }

    @Test
    void construct_whenRepositoryReferenceIsEmpty_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CreateCodeRepositoryDto("", null));

        assertEquals("Repository reference cannot be null or blank", exception.getMessage());
    }

    @Test
    void construct_whenRepositoryReferenceIsBlank_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new CreateCodeRepositoryDto("    ", null));

        assertEquals("Repository reference cannot be null or blank", exception.getMessage());
    }

    @Test
    void construct_whenTokenIdIsNull_shouldReturnNewInstance() {
        String repositoryReference = "owner/repo";

        CreateCodeRepositoryDto dto = new CreateCodeRepositoryDto(repositoryReference, null);

        assertEquals(repositoryReference, dto.repositoryReference());
        assertNull(dto.tokenId());
    }

    @Test
    void construct_whenTokenIdIsNotNull_shouldReturnNewInstance() {
        String repositoryReference = "owern/repo";
        UUID tokenId = UUID.randomUUID();

        CreateCodeRepositoryDto dto = new CreateCodeRepositoryDto(repositoryReference, tokenId);

        assertEquals(repositoryReference, dto.repositoryReference());
        assertEquals(tokenId, dto.tokenId());
    }
}
