package com.prtracker.domain.exceptions;

public class InvalidPullRequestIdException extends RuntimeException {
    public InvalidPullRequestIdException(String message) {
        super("Invalid pull request ID: " + message);
    }
}
