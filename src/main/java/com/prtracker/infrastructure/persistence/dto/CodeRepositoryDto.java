package com.prtracker.infrastructure.persistence.dto;

public record CodeRepositoryDto(
        Long id,
        String owner,
        String name,
        String url
) {}