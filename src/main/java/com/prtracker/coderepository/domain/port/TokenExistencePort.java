package com.prtracker.coderepository.domain.port;

import com.prtracker.shared.kernel.TokenId;

public interface TokenExistencePort {
    boolean exists(TokenId tokenId);
}
