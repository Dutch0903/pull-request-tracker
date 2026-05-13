package com.prtracker.pullrequest.application;

import com.prtracker.pullrequest.domain.model.PullRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class PullRequestDomainConfiguration {

    @Bean
    PullRequestFactory pullRequestFactory() {
        return new PullRequestFactory();
    }
}
