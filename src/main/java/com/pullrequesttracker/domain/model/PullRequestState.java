package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.valueobject.MergeInfo;

public sealed interface PullRequestState permits PullRequestState.Open, PullRequestState.Merged,
        PullRequestState.Closed, PullRequestState.Ignored {

    record Open() implements PullRequestState {}

    record Merged(MergeInfo mergeInfo) implements PullRequestState {}

    record Closed() implements PullRequestState {}

    record Ignored() implements PullRequestState {}
}
