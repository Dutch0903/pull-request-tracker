package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserProvider {
    private final Map<Platform, PlatformUserProvider> providers;

    public UserProvider(List<PlatformUserProvider> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toMap(PlatformUserProvider::platform, Function.identity()));
    }

    public TokenUsername fetchUsername(Platform platform, TokenValue tokenValue) {
        PlatformUserProvider provider = providers.get(platform);
        if (provider == null) {
            throw new IllegalStateException("No user provider registered for platform: " + platform);
        }
        return provider.fetchUsername(tokenValue);
    }
}
