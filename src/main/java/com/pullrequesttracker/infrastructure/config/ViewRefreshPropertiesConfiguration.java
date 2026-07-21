package com.pullrequesttracker.infrastructure.config;

import com.pullrequesttracker.presentation.cli.navigation.ViewRefreshConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ViewRefreshPropertiesConfiguration {

    @Bean
    public ViewRefreshConfiguration viewRefreshConfiguration(
            @Value("${view-refresh.interval-ms}") long intervalMs) {
        return new ViewRefreshConfiguration(intervalMs);
    }
}
