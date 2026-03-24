package com.prtracker.application.parser;

import com.prtracker.domain.enums.CodeRepositoryIdentifierType;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CodeRepositoryIdentifierParser {
    private static final Pattern HTTPS_PATTERN = Pattern.compile("https://github\\.com/([^/]+)/([^/]+?)(?:\\.git)?/?$");

    private static final Pattern SSH_PATTERN = Pattern.compile("git@github\\.com:([^/]+)/([^/]+?)(?:\\.git)?$");

    private static final Pattern OWNER_NAME_PATTERN = Pattern.compile("^([a-zA-Z0-9-]+)/([a-zA-Z0-9-_.]+)$");

    public ParsedCodeRepository parse(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Repository identifier cannot be null or empty");
        }

        Matcher httpsMatcher = HTTPS_PATTERN.matcher(input.trim());
        if (httpsMatcher.matches()) {
            return new ParsedCodeRepository(httpsMatcher.group(1), httpsMatcher.group(2),
                    CodeRepositoryIdentifierType.HTTPS_URL);
        }

        Matcher sshMatcher = SSH_PATTERN.matcher(input.trim());
        if (sshMatcher.matches()) {
            return new ParsedCodeRepository(sshMatcher.group(1), sshMatcher.group(2),
                    CodeRepositoryIdentifierType.SSH_URL);
        }

        Matcher ownerNameMatcher = OWNER_NAME_PATTERN.matcher(input.trim());
        if (ownerNameMatcher.matches()) {
            return new ParsedCodeRepository(ownerNameMatcher.group(1), ownerNameMatcher.group(2),
                    CodeRepositoryIdentifierType.OWNER_NAME);
        }

        throw new IllegalArgumentException(
                "Invalid repository identifier. Expected: owner/name, HTTPS URL, or SSH URL. Got: " + input);
    }
}
