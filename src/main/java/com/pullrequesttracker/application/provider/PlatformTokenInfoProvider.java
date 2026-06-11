package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenValue;

public interface PlatformTokenInfoProvider {
    Platform platform();

    TokenInfo fetchTokenInfo(TokenValue tokenValue);
}
