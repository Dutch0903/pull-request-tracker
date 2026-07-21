package com.pullrequesttracker.presentation.cli.view.attention;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.action.NeedsAttentionViewAction;
import com.pullrequesttracker.presentation.cli.action.SharedAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NeedsAttentionKeyHandler extends KeyHandler {
    public NeedsAttentionKeyHandler(List<SharedAction> shared, List<NeedsAttentionViewAction> actions) {
        super(shared, actions);
    }
}
