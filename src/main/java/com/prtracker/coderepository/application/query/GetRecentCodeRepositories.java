package com.prtracker.coderepository.application.query;

import com.prtracker.coderepository.application.query.port.CodeRepositoryReadPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetRecentCodeRepositories {
    private final CodeRepositoryReadPort codeRepositoryReadPort;

    public List<CodeRepositoryProjection> execute() {
        return codeRepositoryReadPort.findAllAsViews();
    }
}
