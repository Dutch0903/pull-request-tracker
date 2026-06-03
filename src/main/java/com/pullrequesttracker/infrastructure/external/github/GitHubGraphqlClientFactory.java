package com.pullrequesttracker.infrastructure.external.github;

import com.pullrequesttracker.domain.valueobject.TokenValue;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GitHubGraphqlClientFactory {

    public HttpGraphQlClient build(@Nullable TokenValue token) {
        WebClient webClient = WebClient.create("https://api.github.com/graphql");
        HttpGraphQlClient client = HttpGraphQlClient.create(webClient);

        if (token == null) {
            return client;
        }

        return client.mutate().header("Authorization", "Bearer " + token).build();
    }
}
