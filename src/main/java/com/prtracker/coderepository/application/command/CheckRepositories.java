package com.prtracker.coderepository.application.command;

import com.prtracker.coderepository.application.event.RepositoryCheckRequestedEvent;
import com.prtracker.coderepository.domain.port.CodeRepositoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckRepositories {
    private final CodeRepositoryRepository codeRepositoryRepository;
    private final ApplicationEventPublisher eventPublisher;

    public void execute() {
        codeRepositoryRepository.findAll()
                .forEach(repo -> eventPublisher.publishEvent(new RepositoryCheckRequestedEvent(repo)));
    }
}
