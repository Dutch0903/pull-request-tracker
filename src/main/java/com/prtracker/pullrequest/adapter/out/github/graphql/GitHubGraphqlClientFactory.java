package com.prtracker.pullrequest.adapter.out.github.graphql;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.pullrequest.domain.port.TokenValuePort;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GitHubGraphqlClientFactory {
    private final TokenValuePort tokenValuePort;

    public HttpGraphQlClient build(CodeRepository codeRepository) {
        Objects.requireNonNull(codeRepository, "codeRepository can not be null");

        WebClient webClient = WebClient.create("https://api.github.com/graphql");
        HttpGraphQlClient client = HttpGraphQlClient.create(webClient);
        if (codeRepository.getTokenId() == null) {
            return client;
        }

        String token = tokenValuePort.getTokenValue(codeRepository.getTokenId());
        return client.mutate().header("Authorization", "Bearer " + token).build();
    }
}
