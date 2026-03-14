package com.prtracker.presentation.cli.view.dashboard;

import com.prtracker.application.query.GetRecentCodeRepositoriesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DashboardController {
	private final DashboardState dashboardState;

	private final GetRecentCodeRepositoriesQuery getRecentCodeRepositoriesQuery;

	public void loadRecentRepositories() {
		var repos = getRecentCodeRepositoriesQuery.execute();

		dashboardState.setRecentRepositories(repos);
	}
}
