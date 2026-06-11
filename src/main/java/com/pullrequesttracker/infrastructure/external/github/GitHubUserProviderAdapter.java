package com.pullrequesttracker.infrastructure.external.github;

import com.pullrequesttracker.application.provider.PlatformUserProvider;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GitHubUserProviderAdapter implements PlatformUserProvider {
    private final GitHubGraphqlClientFactory clientFactory;

    @Override
    public Platform platform() {
        return Platform.GITHUB;
    }

    @Override
    public TokenUsername fetchUsername(TokenValue tokenValue) {
        HttpGraphQlClient client = clientFactory.build(tokenValue);
        String login = client.documentName("github-viewer").retrieve("viewer.login").toEntity(String.class).block();

        if (login == null || login.isBlank()) {
            throw new IllegalStateException("Could not retrieve GitHub username — token may be invalid");
        }

        return new TokenUsername(login);
    }
}
