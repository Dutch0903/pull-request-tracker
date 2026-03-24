package com.prtracker.application.command.dto;

import java.util.UUID;

public record UpdateTokenDto(UUID id, String name, String value) {
}
