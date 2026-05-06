package com.prtracker.pullrequest.domain.port;

import com.prtracker.shared.kernel.TokenId;

public interface TokenValuePort {
    String getTokenValue(TokenId tokenId);
}
