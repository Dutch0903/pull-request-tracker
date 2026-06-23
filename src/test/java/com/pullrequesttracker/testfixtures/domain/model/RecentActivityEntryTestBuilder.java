package com.pullrequesttracker.testfixtures.domain.model;

import com.pullrequesttracker.domain.model.RecentActivityEntry;
import com.pullrequesttracker.domain.type.RecentActivityType;

import java.time.Instant;

public class RecentActivityEntryTestBuilder {
    private String author = "author";
    private RecentActivityType type = RecentActivityType.OPENED;
    private int pullRequestNumber = 1;
    private Instant occurredAt = Instant.now();

    public static RecentActivityEntryTestBuilder aRecentActivityEntry() {
        return new RecentActivityEntryTestBuilder();
    }

    public RecentActivityEntryTestBuilder withAuthor(String author) {
        this.author = author;
        return this;
    }

    public RecentActivityEntryTestBuilder withType(RecentActivityType type) {
        this.type = type;
        return this;
    }

    public RecentActivityEntryTestBuilder withPullRequestNumber(int pullRequestNumber) {
        this.pullRequestNumber = pullRequestNumber;
        return this;
    }

    public RecentActivityEntryTestBuilder withOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
        return this;
    }

    public RecentActivityEntry build() {
        return new RecentActivityEntry(author, type, pullRequestNumber, occurredAt);
    }
}
