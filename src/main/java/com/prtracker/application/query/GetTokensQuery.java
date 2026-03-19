package com.prtracker.application.query;

import com.prtracker.application.dto.TokenProjection;
import com.prtracker.application.repository.TokenReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetTokensQuery extends VoidQuery<List<TokenProjection>> {
    private final TokenReadRepository tokenReadRepository;

    @Override
    protected List<TokenProjection> executeInternal() {
        return tokenReadRepository.findAllAsViews();
    }
}
