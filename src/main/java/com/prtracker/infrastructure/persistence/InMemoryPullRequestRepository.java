package com.prtracker.infrastructure.persistence;

import com.prtracker.domain.entity.PullRequest;
import com.prtracker.domain.repository.PullRequestRepository;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.PullRequestId;
import com.prtracker.infrastructure.persistence.dto.PullRequestDto;
import com.prtracker.infrastructure.persistence.mapper.PullRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryPullRequestRepository implements PullRequestRepository {
    private static final String FILE_NAME = "pull-requests.json";
    private final FileStorage fileStorage;
    private final PullRequestMapper mapper;

    private final ConcurrentHashMap<PullRequestId, PullRequest> pullRequests = new ConcurrentHashMap<>();

    @Override
    public void save(PullRequest pullRequest) {
        pullRequests.put(pullRequest.getId(), pullRequest);
    }

    @Override
    public void delete(PullRequest pullRequest) {
        pullRequests.remove(pullRequest.getId());
    }

    @Override
    public Optional<PullRequest> findById(PullRequestId id) {
        return Optional.ofNullable(pullRequests.get(id));
    }

    @Override
    public List<PullRequest> findAll() {
        return List.copyOf(pullRequests.values());
    }

    @Override
    public List<PullRequest> findAllByCodeRepositoryId(CodeRepositoryId codeRepositoryId) {
        return List.copyOf(pullRequests.values().stream()
                .filter(pullRequest -> pullRequest.getCodeRepositoryId().equals(codeRepositoryId)).toList());
    }

    @Override
    public void initialize() {
        List<PullRequestDto> loadedPullRequest = fileStorage.load(FILE_NAME, PullRequestDto.class);

        pullRequests.putAll(loadedPullRequest.stream().map(mapper::toDomain)
                .collect(Collectors.toMap(PullRequest::getId, Function.identity())));
    }

    @Override
    public void persist() throws IOException {
        fileStorage.save(FILE_NAME, pullRequests.values().stream().map(mapper::toDto).toList());
    }
}
