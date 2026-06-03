package com.pullrequesttracker.application.dto;

import java.util.UUID;

public record CodeRepositoryDto(UUID id, String owner, String name) {
}
