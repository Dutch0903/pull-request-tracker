package com.pullrequesttracker.presentation.cli.view.attention;

import com.pullrequesttracker.application.usecase.FetchCreatedPrs;
import com.pullrequesttracker.application.usecase.FetchRequestedReviewPrs;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NeedsAttentionController {
    private final NeedsAttentionState state;
    private final FetchCreatedPrs fetchCreatedPrs;
    private final FetchRequestedReviewPrs fetchRequestedReviewPrs;

    public void loadAttentionItems() {
        state.set(NeedsAttentionState.CREATED_PRS, fetchCreatedPrs.execute());
        state.set(NeedsAttentionState.REQUESTED_REVIEW_PRS, fetchRequestedReviewPrs.execute());
    }
}
