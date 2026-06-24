package com.pullrequesttracker.application.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RecentActivityTypeTest {
    @Test
    void toString_shouldTransformNameToLowercase() {
        assertEquals("approved", RecentActivityType.APPROVED.toString());
    }
}
