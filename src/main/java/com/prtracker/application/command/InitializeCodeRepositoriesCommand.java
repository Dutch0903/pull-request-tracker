package com.prtracker.application.command;

import com.prtracker.domain.repository.CodeRepositoryRepository;
import com.prtracker.domain.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitializeCodeRepositoriesCommand extends VoidCommand {
	private final TokenRepository tokenRepository;
	private final CodeRepositoryRepository codeRepositoryRepository;

	@Override
	protected void executeInternal() {
		log.info("Initializing code repositories from file");

		try {
			tokenRepository.initialize();
			codeRepositoryRepository.initialize();

			log.info("Successfully initialized {} code repositories", codeRepositoryRepository.count());
		} catch (Throwable e) {
			log.error("Failed to initialize code repositories", e);
		}
	}
}
