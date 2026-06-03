package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.sync.PullRequestSyncData;
import com.pullrequesttracker.domain.type.Platform;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class PullRequestProvider {

    private final Map<Platform, PlatformPullRequestProvider> providers;

    public PullRequestProvider(List<PlatformPullRequestProvider> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toMap(PlatformPullRequestProvider::platform, Function.identity()));
    }

    public List<PullRequestSyncData> fetch(CodeRepository codeRepository) {
        Platform platform = codeRepository.getPlatform();
        PlatformPullRequestProvider provider = providers.get(platform);
        if (provider == null) {
            throw new IllegalStateException("No provider registered for platform: " + platform);
        }
        return provider.fetch(codeRepository);
    }
}
