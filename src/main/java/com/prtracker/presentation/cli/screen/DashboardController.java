package com.prtracker.presentation.cli.screen;

import com.prtracker.application.usecase.GetCodeRepositoriesUseCase;
import com.prtracker.presentation.cli.KeyBinding;
import com.prtracker.presentation.cli.Screen;
import io.github.kylekreuter.tamboui.spring.annotation.OnKey;
import io.github.kylekreuter.tamboui.spring.annotation.TamboScreen;
import io.github.kylekreuter.tamboui.spring.core.NavigationRouter;
import io.github.kylekreuter.tamboui.spring.core.TemplateModel;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RequiredArgsConstructor
@TamboScreen(value = Screen.DASHBOARD, template = "dashboard")
public class DashboardController extends BaseScreenController {
	private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
	private final NavigationRouter navigationRouter;
	private final GetCodeRepositoriesUseCase getCodeRepositoriesUseCase;

	private static final String ADD_REPOSITORY_KEY = "a";
	private static final String QUIT_KEY = "q";

	@Override
	protected List<KeyBinding> getKeyBindings() {
		return List.of(KeyBinding.create(ADD_REPOSITORY_KEY, "Add Repository"), KeyBinding.create(QUIT_KEY, "Quit"));
	}

	@Override
	public void populate(TemplateModel templateModel) {
		super.populate(templateModel);

		var repositories = getCodeRepositoriesUseCase.execute(null);

		templateModel.put("repositories", repositories);
	}

	@OnKey(ADD_REPOSITORY_KEY)
	void addRepository() {
		navigationRouter.navigateTo(Screen.ADD_REPOSITORY);
	}
}
