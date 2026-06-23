package com.pullrequesttracker.infrastructure.config;

import com.pullrequesttracker.domain.service.stats.ContinuousIntegrationStatisticsCalculator;
import com.pullrequesttracker.domain.service.stats.PullRequestStatisticsCalculator;
import com.pullrequesttracker.domain.service.stats.RecentActivityCalculator;
import com.pullrequesttracker.domain.service.stats.ReviewStatisticsCalculator;
import com.pullrequesttracker.domain.service.stats.StatisticsConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StatisticsCalculatorConfiguration {

    @Bean
    public StatisticsConfiguration statisticsConfiguration(
            @Value("${repository-stats.stale-threshold-days}") int staleThresholdDays,
            @Value("${repository-stats.recent-activity-max-entries}") int recentActivityMaxEntries) {
        return new StatisticsConfiguration(staleThresholdDays, recentActivityMaxEntries);
    }

    @Bean
    public ContinuousIntegrationStatisticsCalculator continuousIntegrationStatisticsCalculator(
            StatisticsConfiguration config) {
        return new ContinuousIntegrationStatisticsCalculator(config);
    }

    @Bean
    public PullRequestStatisticsCalculator pullRequestStatisticsCalculator(StatisticsConfiguration config) {
        return new PullRequestStatisticsCalculator(config);
    }

    @Bean
    public RecentActivityCalculator recentActivityCalculator(StatisticsConfiguration config) {
        return new RecentActivityCalculator(config);
    }

    @Bean
    public ReviewStatisticsCalculator reviewStatisticsCalculator(StatisticsConfiguration config) {
        return new ReviewStatisticsCalculator(config);
    }
}
