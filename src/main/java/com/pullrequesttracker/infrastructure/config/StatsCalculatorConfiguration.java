package com.pullrequesttracker.infrastructure.config;

import com.pullrequesttracker.domain.service.stats.StatsConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatsCalculatorConfiguration {

    @Bean
    public StatsConfiguration statsConfiguration(
            @Value("${repository-stats.stale-threshold-days}") int staleThresholdDays,
            @Value("${repository-stats.recent-activity-max-entries}") int recentActivityMaxEntries) {
        return new StatsConfiguration(staleThresholdDays, recentActivityMaxEntries);
    }
}
