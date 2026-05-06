package com.prtracker.pullrequest.adapter.out.github;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.pullrequest.domain.port.TokenValuePort;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GitHubClientFactory {
    private final TokenValuePort tokenValuePort;

    public GitHub build(CodeRepository codeRepository) throws IOException {
        if (codeRepository == null || codeRepository.getTokenId() == null) {
            return GitHub.connectAnonymously();
        }

        String token = tokenValuePort.getTokenValue(codeRepository.getTokenId());
        return new GitHubBuilder().withOAuthToken(token).build();
    }
}
