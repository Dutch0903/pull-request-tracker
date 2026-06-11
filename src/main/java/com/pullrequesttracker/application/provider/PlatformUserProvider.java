package com.pullrequesttracker.application.provider;

import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.TokenUsername;
import com.pullrequesttracker.domain.valueobject.TokenValue;

public interface PlatformUserProvider {
    Platform platform();
    TokenUsername fetchUsername(TokenValue tokenValue);
}
