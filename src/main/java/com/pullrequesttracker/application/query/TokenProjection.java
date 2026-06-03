package com.pullrequesttracker.application.query;

import java.util.UUID;

public record TokenProjection(UUID id, String name, String value) {
}
