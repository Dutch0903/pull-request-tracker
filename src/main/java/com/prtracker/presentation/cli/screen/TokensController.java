package com.prtracker.presentation.cli.screen;

import com.prtracker.presentation.cli.KeyBinding;
import com.prtracker.presentation.cli.Screen;
import io.github.kylekreuter.tamboui.spring.annotation.OnKey;
import io.github.kylekreuter.tamboui.spring.annotation.TamboScreen;
import io.github.kylekreuter.tamboui.spring.core.NavigationRouter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@TamboScreen(value = Screen.TOKENS, template = "tokens")
public class TokensController extends BaseScreenController {
	private static final String ADD_TOKEN_KEY = "a";
	private static final String BACK_KEY = "escape";

	private static final List<KeyBinding> KEY_BINDINGS = List.of(
			KeyBinding.create(ADD_TOKEN_KEY, "Add Token"),
			KeyBinding.create(BACK_KEY, "Back")
	);

	private final NavigationRouter navigationRouter;

	@OnKey(ADD_TOKEN_KEY)
	public void navigateToAddToken() {
		navigationRouter.navigateTo(Screen.ADD_TOKEN);
	}

	@OnKey(BACK_KEY)
	public void navigateToDashboard() {
		navigationRouter.navigateTo(Screen.DASHBOARD);
	}

	@Override
	protected List<KeyBinding> getKeyBindings() {
		return KEY_BINDINGS;
	}
}
