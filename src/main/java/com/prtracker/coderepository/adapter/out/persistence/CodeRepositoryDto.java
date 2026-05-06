package com.prtracker.coderepository.adapter.out.persistence;

import java.util.UUID;

public record CodeRepositoryDto(UUID id, String owner, String name, String status, UUID tokenId) {
}
