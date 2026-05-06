package com.prtracker.pullrequest.adapter.out.github;

public class GitHubApiException extends RuntimeException {
    public GitHubApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
