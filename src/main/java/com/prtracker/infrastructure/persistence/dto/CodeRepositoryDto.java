package com.prtracker.infrastructure.persistence.dto;

import java.util.UUID;

public record CodeRepositoryDto(UUID id, String owner, String name, String status, UUID tokenId) {
}
