package com.prtracker.coderepository.application.event;

import com.prtracker.coderepository.domain.model.CodeRepository;

public record RepositoryCheckRequestedEvent(CodeRepository codeRepository) {
}
