package com.pullrequesttracker.presentation.cli.view.token;

import com.pullrequesttracker.application.dto.TokenDto;
import com.pullrequesttracker.presentation.cli.state.SnapshotKey;
import com.pullrequesttracker.presentation.cli.state.StateManager;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class TokenManagerState extends StateManager {
    public static final SnapshotKey<List<TokenDto>> TOKENS = new SnapshotKey<>("tokens", Duration.ofMinutes(10));
}
