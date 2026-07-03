package com.pullrequesttracker.presentation.cli.view.token;

import com.pullrequesttracker.presentation.cli.action.KeyHandler;
import com.pullrequesttracker.presentation.cli.action.SharedAction;
import com.pullrequesttracker.presentation.cli.action.TokenManagerViewAction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenManagerKeyHandler extends KeyHandler {
    public TokenManagerKeyHandler(List<SharedAction> shared, List<TokenManagerViewAction> actions) {
        super(shared, actions);
    }
}
