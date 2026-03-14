package com.prtracker.presentation.cli.view.repositories;

import com.prtracker.presentation.cli.View;
import com.prtracker.presentation.cli.ViewComponent;
import dev.tamboui.layout.Rect;
import dev.tamboui.terminal.Frame;
import dev.tamboui.toolkit.element.Element;
import dev.tamboui.toolkit.element.RenderContext;
import dev.tamboui.toolkit.element.Size;
import dev.tamboui.toolkit.event.EventResult;
import dev.tamboui.tui.event.KeyEvent;
import lombok.RequiredArgsConstructor;

import static dev.tamboui.toolkit.Toolkit.*;

@RequiredArgsConstructor
@ViewComponent(name = View.REPOSITORIES)
public class RepositoriesView implements Element {
	private final RepositoriesKeyHandler keyHandler;

	@Override
	public void render(Frame frame, Rect area, RenderContext context) {
		Element ui = dock().center(
				column(
						text("Repositories"),
						spacer(20),
						text("list")
				)
		);

		ui.render(frame, area, context);
	}

	@Override
	public Size preferredSize(int availableWidth, int availableHeight, RenderContext context) {
		return Size.UNKNOWN;
	}

	@Override
	public EventResult handleKeyEvent(KeyEvent event, boolean focused) {
		return keyHandler.handle(event);
	}
}
