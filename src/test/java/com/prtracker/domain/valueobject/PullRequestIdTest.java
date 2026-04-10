package com.prtracker.domain.valueobject;

import com.prtracker.domain.exceptions.InvalidPullRequestIdException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class PullRequestIdTest {
    @Test
    void from_whenValueIsValid_shouldCreatePullRequestId() {
        UUID uuid = UUID.randomUUID();

        PullRequestId pullRequestId = PullRequestId.from(uuid);

        assertNotNull(pullRequestId);
        assertEquals(uuid, pullRequestId.value());
    }

    @Test
    void from_whenValueIsNull_shouldThrowInvalidTokenIdException() {
        InvalidPullRequestIdException exception = assertThrows(InvalidPullRequestIdException.class, () -> PullRequestId.from(null));

        assertEquals("Invalid pull request ID: Pull request ID cannot be null", exception.getMessage());
    }

    @Test
    void equals_whenPullRequestIdsHaveSameValue_shouldBeEqual() {
        UUID uuid = UUID.randomUUID();
        PullRequestId pullRequestId1 = PullRequestId.from(uuid);
        PullRequestId pullRequestId2 = PullRequestId.from(uuid);

        assertEquals(pullRequestId1, pullRequestId2);
        assertEquals(pullRequestId1.hashCode(), pullRequestId1.hashCode());
    }

    @Test
    void equals_whenPullRequestIdsHaveDifferentValue_shouldNotBeEqual() {
        PullRequestId pullRequestId1 = PullRequestId.from(UUID.randomUUID());
        PullRequestId pullRequestId2 = PullRequestId.from(UUID.randomUUID());

        assertNotEquals(pullRequestId1, pullRequestId2);
    }

    @Test
    void create_whenCalledMultipleTimes_shouldGenerateUniqueIds() {
        PullRequestId pullRequestId1 = PullRequestId.create();
        PullRequestId pullRequestId2 = PullRequestId.create();

        assertNotEquals(pullRequestId1, pullRequestId2);
        assertNotEquals(pullRequestId1.value(), pullRequestId2.value());
    }
}
