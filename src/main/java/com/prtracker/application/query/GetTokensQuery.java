package com.prtracker.application.query;

import com.prtracker.application.dto.TokenView;
import com.prtracker.application.repository.TokenReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetTokensQuery extends VoidQuery<List<TokenView>> {
	private final TokenReadRepository tokenReadRepository;

	@Override
	protected List<TokenView> executeInternal() {
		return tokenReadRepository.findAllAsViews();
	}
}
