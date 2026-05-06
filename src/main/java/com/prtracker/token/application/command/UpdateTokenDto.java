package com.prtracker.token.application.command;

import java.util.UUID;

public record UpdateTokenDto(UUID id, String name, String value) {
}
