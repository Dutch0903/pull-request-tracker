package com.prtracker.pullrequest.domain.exception;

public class InvalidPullRequestIdException extends RuntimeException {
    public InvalidPullRequestIdException(String message) {
        super("Invalid pull request ID: " + message);
    }
}
