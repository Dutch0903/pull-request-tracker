package com.pullrequesttracker.domain.model;

import com.pullrequesttracker.domain.valueobject.TokenId;

public sealed interface RepositoryAccess permits RepositoryAccess.Public, RepositoryAccess.Authenticated {

    record Public() implements RepositoryAccess {
    }

    record Authenticated(TokenId tokenId) implements RepositoryAccess {
    }
}
