package com.prtracker.infrastructure;

import com.prtracker.application.command.InitializeCodeRepositoriesCommand;
import com.prtracker.application.command.PersistCodeRepositoriesCommand;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LifeCycleManager {
	private final InitializeCodeRepositoriesCommand initializeRepositoriesCommand;
	private final PersistCodeRepositoriesCommand persistCodeRepositoriesCommand;

	@PostConstruct
	public void onPostConstruct() {
		initializeRepositoriesCommand.execute();
	}

	@PreDestroy
	public void onPreDestroy() {
		persistCodeRepositoriesCommand.execute();
	}
}
