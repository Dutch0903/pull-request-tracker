package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PullRequestIdTest {
    @Test
    void from_whenValueIsValid_shouldCreatePullRequestId() {
        UUID uuid = UUID.randomUUID();

        PullRequestId id = PullRequestId.from(uuid);

        assertNotNull(id);
        assertEquals(uuid, id.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> PullRequestId.from(null));
    }

    @Test
    void equals_whenIdsHaveSameValue_shouldBeEqual() {
        UUID uuid = UUID.randomUUID();
        assertEquals(PullRequestId.from(uuid), PullRequestId.from(uuid));
    }

    @Test
    void equals_whenIdsHaveDifferentValues_shouldNotBeEqual() {
        assertNotEquals(PullRequestId.create(), PullRequestId.create());
    }

    @Test
    void create_whenCalledMultipleTimes_shouldGenerateUniqueIds() {
        assertNotEquals(PullRequestId.create(), PullRequestId.create());
    }
}
