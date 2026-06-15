package com.pullrequesttracker.infrastructure.config;

import com.pullrequesttracker.domain.service.stats.CiStatsCalculator;
import com.pullrequesttracker.domain.service.stats.OpenPrStatsCalculator;
import com.pullrequesttracker.domain.service.stats.RecentActivityCalculator;
import com.pullrequesttracker.domain.service.stats.ReviewStatsCalculator;
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

    @Bean
    public CiStatsCalculator ciStatsCalculator(StatsConfiguration config) {
        return new CiStatsCalculator(config);
    }

    @Bean
    public OpenPrStatsCalculator openPrStatsCalculator(StatsConfiguration config) {
        return new OpenPrStatsCalculator(config);
    }

    @Bean
    public RecentActivityCalculator recentActivityCalculator(StatsConfiguration config) {
        return new RecentActivityCalculator(config);
    }

    @Bean
    public ReviewStatsCalculator reviewStatsCalculator(StatsConfiguration config) {
        return new ReviewStatsCalculator(config);
    }
}
