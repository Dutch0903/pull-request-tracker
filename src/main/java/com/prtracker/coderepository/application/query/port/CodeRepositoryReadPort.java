package com.prtracker.coderepository.application.query.port;

import com.prtracker.coderepository.application.query.CodeRepositoryProjection;

import java.util.List;

public interface CodeRepositoryReadPort {
    List<CodeRepositoryProjection> findAllAsViews();
}
