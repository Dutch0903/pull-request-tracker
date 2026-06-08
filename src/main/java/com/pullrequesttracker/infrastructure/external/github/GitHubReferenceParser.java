package com.pullrequesttracker.infrastructure.external.github;

import com.pullrequesttracker.application.parser.ParsedCodeRepositoryReference;
import com.pullrequesttracker.domain.type.CodeRepositoryReferenceType;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.application.parser.PlatformCodeRepositoryReferenceParser;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GitHubReferenceParser implements PlatformCodeRepositoryReferenceParser {
    private static final Pattern HTTPS_PATTERN = Pattern.compile("https://github\\.com/([^/]+)/([^/]+?)(?:\\.git)?/?$");
    private static final Pattern SSH_PATTERN = Pattern.compile("git@github\\.com:([^/]+)/([^/]+?)(?:\\.git)?$");
    private static final Pattern OWNER_NAME_PATTERN = Pattern.compile("^([a-zA-Z0-9-]+)/([a-zA-Z0-9-_.]+)$");

    @Override
    public Platform platform() {
        return Platform.GITHUB;
    }

    @Override
    public ParsedCodeRepositoryReference parse(String input) {
        Matcher httpsMatcher = HTTPS_PATTERN.matcher(input.trim());
        if (httpsMatcher.matches()) {
            return new ParsedCodeRepositoryReference(httpsMatcher.group(1), httpsMatcher.group(2),
                    CodeRepositoryReferenceType.HTTPS_URL);
        }

        Matcher sshMatcher = SSH_PATTERN.matcher(input.trim());
        if (sshMatcher.matches()) {
            return new ParsedCodeRepositoryReference(sshMatcher.group(1), sshMatcher.group(2),
                    CodeRepositoryReferenceType.SSH_URL);
        }

        Matcher ownerNameMatcher = OWNER_NAME_PATTERN.matcher(input.trim());
        if (ownerNameMatcher.matches()) {
            return new ParsedCodeRepositoryReference(ownerNameMatcher.group(1), ownerNameMatcher.group(2),
                    CodeRepositoryReferenceType.OWNER_NAME);
        }

        throw new IllegalArgumentException(
                "Invalid repository identifier for GitHub. Expected: owner/name, HTTPS URL, or SSH URL. Got: " + input);
    }
}
