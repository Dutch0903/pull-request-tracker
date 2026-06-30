package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActorTest {
    @Test
    void create_whenValueIsValid_shouldCreate() {
        Actor actor = new Actor("octocat");

        assertNotNull(actor);
        assertEquals("octocat", actor.value());
        assertEquals("octocat", actor.toString());
    }

    @Test
    void create_whenValueIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Actor(null));
    }

    @Test
    void create_whenValueIsBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Actor("  "));
    }

    @Test
    void equals_whenActorsHaveSameValue_shouldBeEqual() {
        assertEquals(new Actor("octocat"), new Actor("octocat"));
    }

    @Test
    void equals_whenActorsHaveDifferentValues_shouldNotBeEqual() {
        assertNotEquals(new Actor("octocat"), new Actor("monalisa"));
    }
}
