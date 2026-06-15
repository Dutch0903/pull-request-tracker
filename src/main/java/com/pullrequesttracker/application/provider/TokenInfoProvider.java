package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class TokenInfoProvider {
    private final Map<Platform, PlatformTokenInfoProvider> providers;

    public TokenInfoProvider(List<PlatformTokenInfoProvider> providers) {
        this.providers = providers.stream()
                .collect(Collectors.toMap(PlatformTokenInfoProvider::platform, Function.identity()));
    }

    public TokenInfo fetchTokenInfo(Platform platform, TokenValue tokenValue) {
        PlatformTokenInfoProvider provider = providers.get(platform);
        if (provider == null) {
            throw new TokenInfoException("No token info provider registered for platform: " + platform);
        }
        return provider.fetchTokenInfo(tokenValue);
    }
}
