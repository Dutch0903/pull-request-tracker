package com.pullrequesttracker.presentation.cli.view.attention.action;

import com.pullrequesttracker.application.dto.AttentionItemDto;
import com.pullrequesttracker.presentation.cli.action.NeedsAttentionViewAction;
import com.pullrequesttracker.presentation.cli.action.OpenInBrowserAction;
import com.pullrequesttracker.presentation.cli.view.attention.component.NeedsAttentionGroupedList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OpenAttentionItemInBrowserAction extends OpenInBrowserAction implements NeedsAttentionViewAction {
    private final NeedsAttentionGroupedList groupedList;

    @Override
    protected String getUrl() {
        AttentionItemDto item = groupedList.getSelectedItem();
        return item != null ? item.url() : null;
    }
}
