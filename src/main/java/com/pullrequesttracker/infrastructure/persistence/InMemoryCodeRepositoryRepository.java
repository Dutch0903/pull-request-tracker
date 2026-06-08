package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.repository.CodeRepositoryRepository;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.infrastructure.persistence.dto.CodeRepositoryDto;
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
public class InMemoryCodeRepositoryRepository implements CodeRepositoryRepository {
    private static final String FILE_NAME = "repositories.json";
    private final FileStorage fileStorage;
    private final CodeRepositoryMapper mapper;

    private final ConcurrentHashMap<CodeRepositoryId, CodeRepository> repositories = new ConcurrentHashMap<>();

    @Override
    public void save(CodeRepository codeRepository) {
        repositories.put(codeRepository.getId(), codeRepository);
    }

    @Override
    public boolean exists(FullName fullName) {
        return repositories.values().stream().anyMatch(r -> r.getFullName().equals(fullName));
    }

    public Optional<CodeRepository> findById(CodeRepositoryId id) {
        return Optional.ofNullable(repositories.get(id));
    }

    @Override
    public List<CodeRepository> findAll() {
        return List.copyOf(repositories.values());
    }

    public void delete(CodeRepositoryId id) {
        repositories.remove(id);
    }

    public void initialize() {
        List<CodeRepositoryDto> loaded = fileStorage.load(FILE_NAME, CodeRepositoryDto.class);
        repositories.putAll(loaded.stream().map(mapper::toDomain)
                .collect(Collectors.toMap(CodeRepository::getId, Function.identity())));
    }

    public void persist() throws IOException {
        fileStorage.save(FILE_NAME, repositories.values().stream().map(mapper::toDto).toList());
    }
}
