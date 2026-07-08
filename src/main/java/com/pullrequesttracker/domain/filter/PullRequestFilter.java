package com.pullrequesttracker.domain.filter;

import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;

import java.util.ArrayList;
import java.util.List;

public record PullRequestFilter(PullRequestFilterField field, Object value) {

    public static PullRequestFilter status(PullRequestStatus status) {
        return new PullRequestFilter(PullRequestFilterField.STATUS, status);
    }

    public static PullRequestFilter search(String query) {
        return new PullRequestFilter(PullRequestFilterField.SEARCH, query);
    }

    public static PullRequestFilter codeRepository(CodeRepositoryId repoId) {
        return new PullRequestFilter(PullRequestFilterField.CODE_REPOSITORY, repoId);
    }

    public static PullRequestFilter author(String username) {
        return new PullRequestFilter(PullRequestFilterField.AUTHOR, username);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final List<PullRequestFilter> filters = new ArrayList<>();

        public Builder status(PullRequestStatus status) {
            if (status != null)
                filters.add(PullRequestFilter.status(status));
            return this;
        }

        public Builder search(String query) {
            if (query != null && !query.isBlank())
                filters.add(PullRequestFilter.search(query));
            return this;
        }

        public Builder codeRepository(CodeRepositoryId repoId) {
            if (repoId != null)
                filters.add(PullRequestFilter.codeRepository(repoId));
            return this;
        }

        public Builder author(String username) {
            if (username != null && !username.isBlank())
                filters.add(PullRequestFilter.author(username));
            return this;
        }

        public List<PullRequestFilter> build() {
            return List.copyOf(filters);
        }
    }
}
