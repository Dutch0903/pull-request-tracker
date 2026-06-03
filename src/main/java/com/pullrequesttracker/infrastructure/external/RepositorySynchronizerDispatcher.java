package com.pullrequesttracker.infrastructure.external;

import com.pullrequesttracker.application.synchronizer.RepositorySynchronizer;
import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.Platform;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class RepositorySynchronizerDispatcher implements RepositorySynchronizer {

    private final Map<Platform, PlatformSynchronizer> strategies;

    public RepositorySynchronizerDispatcher(List<PlatformSynchronizer> synchronizers) {
        this.strategies = synchronizers.stream()
                .collect(Collectors.toMap(PlatformSynchronizer::platform, Function.identity()));
    }

    @Override
    public List<PullRequestSyncData> synchronize(CodeRepository codeRepository) {
        Platform platform = codeRepository.getPlatform();
        PlatformSynchronizer synchronizer = strategies.get(platform);
        if (synchronizer == null) {
            throw new IllegalStateException("No synchronizer registered for platform: " + platform);
        }
        return synchronizer.synchronize(codeRepository);
    }
}
