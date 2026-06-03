package com.pullrequesttracker.infrastructure.external;

import com.pullrequesttracker.application.synchronizer.RepositorySynchronizer;
import com.pullrequesttracker.domain.type.Platform;

public interface PlatformSynchronizer extends RepositorySynchronizer {
    Platform platform();
}
