package com.pullrequesttracker.infrastructure.persistence.dto;

import java.util.UUID;

public record TokenDto(UUID id, String name, String value, String platform, String username) {
}
