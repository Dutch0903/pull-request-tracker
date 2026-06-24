package com.pullrequesttracker.domain.valueobject;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class MergeInfoTest {
    @Test
    void create_whenAllFieldsAreValid_shouldCreate() {
        Instant mergedAt = Instant.now();

        MergeInfo mergeInfo = new MergeInfo("octocat", mergedAt);

        assertEquals("octocat", mergeInfo.mergedBy());
        assertEquals(mergedAt, mergeInfo.mergedAt());
    }

    @Test
    void create_whenMergedByIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergeInfo(null, Instant.now()));
    }

    @Test
    void create_whenMergedByIsBlank_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new MergeInfo("  ", Instant.now()));
    }

    @Test
    void create_whenMergedAtIsNull_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> new MergeInfo("octocat", null));
    }
}
