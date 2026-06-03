package com.pullrequesttracker.application.dto;

import java.util.UUID;

public record TokenDto(UUID id, String name, String value) {
}
