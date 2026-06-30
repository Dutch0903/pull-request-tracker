package com.pullrequesttracker.testfixtures.domain.valueobject;

import com.pullrequesttracker.domain.valueobject.Title;

public class PullRequestTitleTestBuilder {
    private String value = "title";

    public static PullRequestTitleTestBuilder aPullRequestTitle() {
        return new PullRequestTitleTestBuilder();
    }

    public PullRequestTitleTestBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public Title build() {
        return new Title(value);
    }
}
