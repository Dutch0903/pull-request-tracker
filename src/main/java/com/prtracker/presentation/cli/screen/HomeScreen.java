package com.prtracker.presentation.cli.screen;

import com.prtracker.presentation.cli.KeyBinding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.jline.tui.component.view.control.BoxView;
import org.springframework.shell.jline.tui.component.view.control.GridView;
import org.springframework.shell.jline.tui.component.view.control.View;
import org.springframework.shell.jline.tui.component.view.event.KeyEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class HomeScreen extends BaseScreen {
    @Override
    public View render() {
        BoxView box = new BoxView();

        GridView grid = new GridView();
        grid.setShowBorder(true);
        grid.setRowSize(0, 1);
        grid.setRowSize(1, 3);
        grid.setRowSize(2, 1);

        box.setTitle("Home");
        box.setDrawFunction(((screen, rectangle) -> {
            screen.writerBuilder().build().text("Home", 0, 0);
            return rectangle;
        }));

        grid.addItem(box, 0, 0, 1, 1, 0, 0);

        return grid;
    }

    @Override
    public ScreenType getType() {
        return ScreenType.HOME;
    }

    @Override
    public List<KeyBinding> getCustomKeyBindings() {
        return List.of(
                KeyBinding.create(
                        KeyEvent.Key.r,
                        "Repositories",
                        keyEvent -> {
                            if (navigationCallback != null) {
                                navigationCallback.navigateTo(ScreenType.REPOSITORIES);
                            }
                        }
                )
        );
    }
}
