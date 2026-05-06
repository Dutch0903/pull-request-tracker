package com.prtracker.token.adapter.out.persistence;

import java.util.UUID;

public record TokenDto(UUID id, String name, String value) {
}
