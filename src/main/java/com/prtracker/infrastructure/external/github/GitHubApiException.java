package com.prtracker.infrastructure.external.github;

public class GitHubApiException extends RuntimeException {
    public GitHubApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
