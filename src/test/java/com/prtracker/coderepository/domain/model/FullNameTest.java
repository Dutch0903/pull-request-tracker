package com.prtracker.coderepository.domain.model;

import com.prtracker.coderepository.domain.exception.InvalidFullNameException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FullNameTest {
    @Test
    void construct_whenOwnerIsNull_shouldThrowException() {
        InvalidFullNameException exception = assertThrows(InvalidFullNameException.class, () -> {
            new FullName(null, "name");
        });

        assertEquals("Invalid full name: Owner cannot be null", exception.getMessage());
    }

    @Test
    void construct_whenNameIsNull_shouldThrowException() {
        InvalidFullNameException exception = assertThrows(InvalidFullNameException.class, () -> {
            new FullName("owner", null);
        });

        assertEquals("Invalid full name: Name cannot be null", exception.getMessage());
    }

    @Test
    void construct_whenOwnerAndNameAreValid_shouldReturnFullName() {
        String owner = "owner";
        String name = "name";
        FullName fullName = new FullName(owner, name);

        assertNotNull(fullName);
        assertEquals(owner, fullName.owner());
        assertEquals(name, fullName.name());
    }

    @Test
    void toString_whenOwnerAndNameAreValid_shouldReturnFullName() {
        String owner = "owner";
        String name = "name";
        FullName fullName = new FullName(owner, name);

        assertNotNull(fullName);
        assertEquals(owner + "/" + name, fullName.toString());
    }
}
