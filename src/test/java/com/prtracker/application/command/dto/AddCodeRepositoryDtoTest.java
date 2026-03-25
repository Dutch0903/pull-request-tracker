package com.prtracker.application.command.dto;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class AddCodeRepositoryDtoTest {
    @Test
    void construct_whenRepositoryReferenceIsNull_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new AddCodeRepositoryDto(null, null));

        assertEquals("Repository reference cannot be null or blank", exception.getMessage());
    }

    @Test
    void construct_whenRepositoryReferenceIsEmpty_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new AddCodeRepositoryDto("", null));

        assertEquals("Repository reference cannot be null or blank", exception.getMessage());
    }

    @Test
    void construct_whenRepositoryReferenceIsBlank_shouldThrowIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new AddCodeRepositoryDto("    ", null));

        assertEquals("Repository reference cannot be null or blank", exception.getMessage());
    }

    @Test
    void construct_whenTokenIdIsNull_shouldReturnNewInstance() {
        String repositoryReference = "owner/repo";

        AddCodeRepositoryDto dto = new AddCodeRepositoryDto(repositoryReference, null);

        assertEquals(repositoryReference, dto.repositoryReference());
        assertNull(dto.tokenId());
    }

    @Test
    void construct_whenTokenIdIsNotNull_shouldReturnNewInstance() {
        String repositoryReference = "owern/repo";
        UUID tokenId = UUID.randomUUID();

        AddCodeRepositoryDto dto = new AddCodeRepositoryDto(repositoryReference, tokenId);

        assertEquals(repositoryReference, dto.repositoryReference());
        assertEquals(tokenId, dto.tokenId());
    }
}
