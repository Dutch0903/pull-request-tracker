package com.pullrequesttracker.domain.service.stats;

public abstract class StatsCalculator {

    protected final StatsConfiguration config;

    protected StatsCalculator(StatsConfiguration config) {
        this.config = config;
    }
}
