package com.pullrequesttracker.domain.service.stats;

public abstract class StatisticsCalculator {

    protected final StatisticsConfiguration config;

    protected StatisticsCalculator(StatisticsConfiguration config) {
        this.config = config;
    }
}
