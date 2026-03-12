package com.prtracker.infrastructure.persistence;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.repository.PullRequestRepository;
import com.prtracker.domain.valueobject.PullRequestId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryPullRequestRepository implements PullRequestRepository {
	@Override
	public void save(PullRequest pullRequest) {

	}

	@Override
	public void delete(PullRequest pullRequest) {

	}

	@Override
	public Optional<PullRequest> findById(PullRequestId id) {
		return Optional.empty();
	}

	@Override
	public List<PullRequest> findAll() {
		return List.of();
	}

	@Override
	public List<PullRequest> findAllByRepository(CodeRepository repository) {
		return List.of();
	}

	@Override
	public void initialize() {

	}

	@Override
	public void persist() {

	}
}
