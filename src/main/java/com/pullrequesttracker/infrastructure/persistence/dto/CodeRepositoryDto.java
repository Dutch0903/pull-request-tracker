package com.pullrequesttracker.infrastructure.persistence.dto;

import org.springframework.lang.Nullable;

import java.util.UUID;

public record CodeRepositoryDto(
        UUID id,
        String owner,
        String name,
        String platform,
        @Nullable UUID tokenId,
        @Nullable String lastCheckedAt
) {
}
