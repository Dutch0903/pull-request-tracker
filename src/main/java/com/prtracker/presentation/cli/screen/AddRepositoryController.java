package com.prtracker.presentation.cli.screen;

import com.prtracker.application.dto.AddCodeRepositoryDto;
import com.prtracker.application.usecase.AddCodeRepositoryUseCase;
import com.prtracker.domain.exceptions.CodeRepositoryAlreadyExistsException;
import com.prtracker.presentation.cli.KeyBinding;
import com.prtracker.presentation.cli.Screen;
import dev.tamboui.widgets.input.TextInputState;
import io.github.kylekreuter.tamboui.spring.annotation.BindState;
import io.github.kylekreuter.tamboui.spring.annotation.OnKey;
import io.github.kylekreuter.tamboui.spring.annotation.TamboScreen;
import io.github.kylekreuter.tamboui.spring.core.NavigationRouter;
import io.github.kylekreuter.tamboui.spring.core.TemplateModel;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@TamboScreen(value = Screen.ADD_REPOSITORY, template = "add-repository")
public class AddRepositoryController extends BaseScreenController {
	private final NavigationRouter navigationRouter;
	private final AddCodeRepositoryUseCase addCodeRepositoryUseCase;

	private static final String SAVE_KEY = "ctrl+s";
	private static final String BACK_KEY = "escape";

	private static final List<KeyBinding> KEY_BINDINGS = List.of(KeyBinding.create(SAVE_KEY, "Save"),
			KeyBinding.create(BACK_KEY, "Back"));

	@BindState("nameInput")
	private final TextInputState nameInput = new TextInputState();

	@BindState("error")
	private String error = "";

	@BindState("tokenInput")
	private final TextInputState tokenInput = new TextInputState();

	@Override
	protected List<KeyBinding> getKeyBindings() {
		return KEY_BINDINGS;
	}

	@Override
	public void populate(TemplateModel templateModel) {
		super.populate(templateModel);
		templateModel.bindState("error", error);
	}

	@OnKey(SAVE_KEY)
	public void save() {
		String name = nameInput.text();
		String token = tokenInput.text();

		AddCodeRepositoryDto dto = new AddCodeRepositoryDto(name, token);

		try {
			addCodeRepositoryUseCase.execute(dto);
			navigationRouter.navigateTo(Screen.DASHBOARD);
		} catch (CodeRepositoryAlreadyExistsException e) {
			error = e.getMessage();
		}
	}

	@OnKey(BACK_KEY)
	public void back() {
		resetAll();
		navigationRouter.navigateTo(Screen.DASHBOARD);
	}

	private void resetAll() {
		nameInput.setText("");
		tokenInput.setText("");
		error = "";
	}
}
