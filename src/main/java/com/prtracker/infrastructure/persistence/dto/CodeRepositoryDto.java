package com.prtracker.infrastructure.persistence.dto;

import java.util.UUID;

public record CodeRepositoryDto(String identifier, String owner, String name, String status, UUID tokenId) {
}
