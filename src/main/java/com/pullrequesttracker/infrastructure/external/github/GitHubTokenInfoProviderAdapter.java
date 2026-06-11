package com.pullrequesttracker.infrastructure.external.github;

import com.pullrequesttracker.application.provider.PlatformTokenInfoProvider;
import com.pullrequesttracker.application.provider.TokenInfo;
import com.pullrequesttracker.domain.exception.InvalidTokenValueException;
import com.pullrequesttracker.domain.exception.TokenApiException;
import com.pullrequesttracker.domain.exception.TokenException;
import com.pullrequesttracker.domain.exception.TokenWithoutExpirationException;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenExpirationDate;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GitHubTokenInfoProviderAdapter implements PlatformTokenInfoProvider {

    @Override
    public Platform platform() {
        return Platform.GITHUB;
    }

    @Override
    public TokenInfo fetchTokenInfo(TokenValue tokenValue) {
        try {
            ResponseEntity<GitHubUserResponse> response = WebClient.builder().baseUrl("https://api.github.com").build()
                    .get().uri("/user").header("Authorization", "Bearer " + tokenValue)
                    .header("Accept", "application/vnd.github+json").retrieve().toEntity(GitHubUserResponse.class)
                    .block();

            if (response == null || response.getBody() == null) {
                throw new TokenApiException("Could not reach the GitHub API", null);
            }

            String login = response.getBody().login();
            if (login == null || login.isBlank()) {
                throw new InvalidTokenValueException("Could not retrieve username — token may be invalid");
            }

            String expirationHeader = response.getHeaders().getFirst("github-authentication-token-expiration");
            if (expirationHeader == null) {
                throw new TokenWithoutExpirationException(
                        "This token has no expiration date — only tokens with an expiration are accepted");
            }

            Instant expiration = parseExpiration(expirationHeader);
            return new TokenInfo(new TokenUsername(login), new TokenExpirationDate(expiration));

        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new InvalidTokenValueException("GitHub rejected this token — check the value");
            }
            throw new TokenApiException("GitHub API returned an error (HTTP " + e.getStatusCode().value() + ")", e);
        } catch (TokenException e) {
            throw e;
        } catch (RuntimeException e) {
            throw new TokenApiException("Could not reach the GitHub API — check your connection", e);
        }
    }

    private Instant parseExpiration(String value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss zzz");
        return ZonedDateTime.parse(value, formatter).toInstant();
    }
}
