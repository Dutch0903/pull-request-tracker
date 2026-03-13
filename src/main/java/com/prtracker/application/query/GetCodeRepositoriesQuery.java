package com.prtracker.application.query;

import com.prtracker.application.dto.CodeRepositoryView;
import com.prtracker.application.repository.CodeRepositoryReadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetCodeRepositoriesQuery extends VoidQuery<List<CodeRepositoryView>> {
	private final CodeRepositoryReadRepository codeRepositoryReadRepository;

	@Override
	protected List<CodeRepositoryView> executeInternal() {
		return codeRepositoryReadRepository.findAllAsViews();
	}
}
