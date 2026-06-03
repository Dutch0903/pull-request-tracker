package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FullNameTest {
    @Test
    void construct_whenOwnerIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FullName(null, "name"));
    }

    @Test
    void construct_whenNameIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new FullName("owner", null));
    }

    @Test
    void construct_whenOwnerIsBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FullName("  ", "name"));
    }

    @Test
    void construct_whenNameIsBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new FullName("owner", "  "));
    }

    @Test
    void construct_whenValid_shouldCreateFullName() {
        FullName fullName = new FullName("owner", "name");

        assertEquals("owner", fullName.owner());
        assertEquals("name", fullName.name());
    }

    @Test
    void toString_shouldReturnOwnerSlashName() {
        assertEquals("owner/name", new FullName("owner", "name").toString());
    }
}
