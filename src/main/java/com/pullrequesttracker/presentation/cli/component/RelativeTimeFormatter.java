package com.pullrequesttracker.presentation.cli.component;

import java.time.Duration;
import java.time.Instant;

public class RelativeTimeFormatter {

    private RelativeTimeFormatter() {
    }

    public static String format(Instant instant) {
        Duration elapsed = Duration.between(instant, Instant.now());
        long minutes = elapsed.toMinutes();
        long hours = elapsed.toHours();
        long days = elapsed.toDays();

        if (minutes < 1)
            return "just now";
        if (hours < 1)
            return minutes + "m ago";
        if (hours < 24)
            return hours + "h ago";
        if (days <= 30)
            return days + "d ago";
        return (days / 30) + "mo ago";
    }
}
