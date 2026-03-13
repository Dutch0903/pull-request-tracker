package com.prtracker.infrastructure.persistence.dto;

import java.util.UUID;

public record TokenDto(UUID id, String name, String value) {
}
