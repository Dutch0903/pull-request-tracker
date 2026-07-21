package com.pullrequesttracker.presentation.cli.view.attention;

import com.pullrequesttracker.application.dto.AttentionItemDto;
import com.pullrequesttracker.presentation.cli.navigation.ViewRefreshConfiguration;
import com.pullrequesttracker.presentation.cli.dialog.DialogManager;
import com.pullrequesttracker.presentation.cli.navigation.View;
import com.pullrequesttracker.presentation.cli.navigation.ViewComponent;
import com.pullrequesttracker.presentation.cli.navigation.ViewName;
import com.pullrequesttracker.presentation.cli.view.attention.component.NeedsAttentionGroupedList;
import dev.tamboui.toolkit.element.Element;

import java.util.List;

@ViewComponent(name = ViewName.NEEDS_ATTENTION)
public class NeedsAttentionView extends View {
    private final NeedsAttentionController controller;
    private final NeedsAttentionState state;
    private final NeedsAttentionGroupedList groupedList;

    public NeedsAttentionView(DialogManager dialogManager, NeedsAttentionKeyHandler keyHandler,
            NeedsAttentionController controller, NeedsAttentionState state,
            NeedsAttentionGroupedList groupedList, ViewRefreshConfiguration viewRefreshProperties) {
        super(dialogManager, keyHandler, viewRefreshProperties);
        this.controller = controller;
        this.state = state;
        this.groupedList = groupedList;
    }

    @Override
    protected void refreshState() {
        controller.loadAttentionItems();
    }

    @Override
    protected Element renderBody() {
        List<AttentionItemDto> createdPrs = state.getOrElse(NeedsAttentionState.CREATED_PRS, List.of());
        List<AttentionItemDto> requestedReviewPrs = state.getOrElse(NeedsAttentionState.REQUESTED_REVIEW_PRS, List.of());
        return groupedList.render(createdPrs, requestedReviewPrs);
    }
}
