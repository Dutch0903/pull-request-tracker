package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.filter.PullRequestFilter;
import com.pullrequesttracker.domain.model.PullRequest;
import com.pullrequesttracker.domain.repository.PullRequestRepository;
import com.pullrequesttracker.domain.type.PullRequestStatus;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.PullRequestId;
import com.pullrequesttracker.infrastructure.persistence.dto.PullRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    public List<PullRequest> findAllByCodeRepositoryId(CodeRepositoryId codeRepositoryId) {
        return pullRequests.values().stream().filter(pr -> pr.getCodeRepositoryId().equals(codeRepositoryId)).toList();
    }

    @Override
    public Map<CodeRepositoryId, Integer> countAllByCodeRepositoryId() {
        return pullRequests.values().stream()
                .collect(Collectors.groupingBy(PullRequest::getCodeRepositoryId, Collectors.summingInt(pr -> 1)));
    }

    public Optional<PullRequest> findById(PullRequestId id) {
        return Optional.ofNullable(pullRequests.get(id));
    }

    public List<PullRequest> findAll() {
        return List.copyOf(pullRequests.values());
    }

    @Override
    public List<PullRequest> findAll(List<PullRequestFilter> filters) {
        return pullRequests.values().stream().filter(pr -> filters.stream().allMatch(f -> matches(pr, f))).toList();
    }

    private boolean matches(PullRequest pr, PullRequestFilter filter) {
        return switch (filter.field()) {
            case STATUS -> pr.getStatus() == filter.value();
            case CODE_REPOSITORY -> pr.getCodeRepositoryId().equals(filter.value());
            case SEARCH -> {
                String term = ((String) filter.value()).toLowerCase();
                yield pr.getTitle().value().toLowerCase().contains(term)
                        || pr.getAuthor().value().toLowerCase().contains(term);
            }
        };
    }

    @Override
    public List<PullRequest> findAllOpen() {
        return pullRequests.values().stream().filter(pr -> pr.getStatus() == PullRequestStatus.OPEN).toList();
    }

    public void delete(PullRequestId id) {
        pullRequests.remove(id);
    }

    public void initialize() {
        List<PullRequestDto> loaded = fileStorage.load(FILE_NAME, PullRequestDto.class);
        pullRequests.putAll(loaded.stream().map(mapper::toDomain)
                .collect(Collectors.toMap(PullRequest::getId, Function.identity())));
    }

    public void persist() throws IOException {
        fileStorage.save(FILE_NAME, pullRequests.values().stream().map(mapper::toDto).toList());
    }
}
