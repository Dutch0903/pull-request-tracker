package com.pullrequesttracker.testfixtures.domain.valueobject;

import com.pullrequesttracker.domain.valueobject.Actor;

public class ActorTestBuilder {
    private String value = "author";

    public static ActorTestBuilder anActor() {
        return new ActorTestBuilder();
    }

    public ActorTestBuilder withValue(String value) {
        this.value = value;
        return this;
    }

    public Actor build() {
        return new Actor(value);
    }
}
