package com.prtracker.token.application.query.port;

import com.prtracker.token.application.query.TokenProjection;

import java.util.List;

public interface TokenReadPort {
    List<TokenProjection> findAllAsProjection();
}
