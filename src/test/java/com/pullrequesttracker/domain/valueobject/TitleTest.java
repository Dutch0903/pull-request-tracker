package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TitleTest {
    @Test
    void create_whenValueIsValid_shouldCreate() {
        Title title = new Title("Add feature X");

        assertNotNull(title);
        assertEquals("Add feature X", title.value());
        assertEquals("Add feature X", title.toString());
    }

    @Test
    void create_whenValueIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Title(null));
    }

    @Test
    void create_whenValueIsBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Title("  "));
    }

    @Test
    void equals_whenTitlesHaveSameValue_shouldBeEqual() {
        assertEquals(new Title("Add feature X"), new Title("Add feature X"));
    }

    @Test
    void equals_whenTitlesHaveDifferentValues_shouldNotBeEqual() {
        assertNotEquals(new Title("Add feature X"), new Title("Fix bug Y"));
    }
}
