package com.pullrequesttracker.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Clock;

@Configuration
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties({RepositoryCheckProperties.class})
@Slf4j
public class AsyncSchedulingConfiguration implements AsyncConfigurer {

    @Bean
    public Clock clock() {
        return Clock.systemUTC();
    }

    @Bean(name = "codeRepositoryCheckExecutor")
    public TaskExecutor codeRepositoryCheckExecutor(RepositoryCheckProperties props) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(props.threadPoolSize());
        executor.setMaxPoolSize(props.threadPoolSize());
        executor.setThreadNamePrefix("repo-check-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(30);
        executor.initialize();
        return executor;
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) -> log.error("Uncaught exception in async method {}: {}", method.getName(),
                ex.getMessage(), ex);
    }
}
