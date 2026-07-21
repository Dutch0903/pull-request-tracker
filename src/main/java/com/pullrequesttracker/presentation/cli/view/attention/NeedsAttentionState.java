package com.pullrequesttracker.presentation.cli.view.attention;

import com.pullrequesttracker.application.dto.AttentionItemDto;
import com.pullrequesttracker.presentation.cli.state.SnapshotKey;
import com.pullrequesttracker.presentation.cli.state.StateManager;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NeedsAttentionState extends StateManager {
    public static final SnapshotKey<List<AttentionItemDto>> CREATED_PRS = new SnapshotKey<>("createdPrs");
    public static final SnapshotKey<List<AttentionItemDto>> REQUESTED_REVIEW_PRS = new SnapshotKey<>("requestedReviewPrs");
}
