package com.prtracker.presentation.cli.screen;

import com.prtracker.presentation.cli.KeyBinding;
import com.prtracker.presentation.cli.NavigationCallback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.jline.tui.component.view.control.BoxView;
import org.springframework.shell.jline.tui.component.view.control.GridView;
import org.springframework.shell.jline.tui.component.view.control.View;
import org.springframework.shell.jline.tui.component.view.event.KeyEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RepositoriesScreen extends BaseScreen {
    private NavigationCallback navigationCallback;

    @Override
    public View render() {
        BoxView box = new BoxView();

        GridView grid = new GridView();
        grid.setShowBorder(true);
        grid.setRowSize(0, 1);
        grid.setRowSize(1, 3);
        grid.setRowSize(2, 1);


        box.setTitle("Repositories");
        box.setDrawFunction(((screen, rectangle) -> {
            screen.writerBuilder().build().text("Repositories", 0, 0);
            return rectangle;
        }));

        grid.addItem(box, 0, 0, 1, 1, 0, 0);

        return grid;
    }

    @Override
    public List<KeyBinding> getCustomKeyBindings() {
        return List.of(
                KeyBinding.create(
                        KeyEvent.Key.h,
                        "Home",
                        keyEvent -> {
                            if (navigationCallback != null) {
                                navigationCallback.navigateTo(ScreenType.HOME);
                            }
                        }
                )
        );
    }

    @Override
    public void setNavigationCallback(NavigationCallback callback) {
        this.navigationCallback = callback;
    }

    @Override
    public ScreenType getType() {
        return ScreenType.REPOSITORIES;
    }
}
