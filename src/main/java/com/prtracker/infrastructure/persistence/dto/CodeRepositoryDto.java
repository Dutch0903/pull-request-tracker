package com.prtracker.infrastructure.persistence.dto;

public record CodeRepositoryDto(String identifier, String owner, String name, String url, String status,
        String accessToken) {
}
