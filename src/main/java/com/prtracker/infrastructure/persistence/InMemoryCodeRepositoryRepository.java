package com.prtracker.infrastructure.persistence;

import com.prtracker.application.query.dto.CodeRepositoryProjection;
import com.prtracker.application.repository.CodeRepositoryReadRepository;
import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.FullName;
import com.prtracker.infrastructure.persistence.dto.CodeRepositoryDto;
import com.prtracker.infrastructure.persistence.mapper.CodeRepositoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryCodeRepositoryRepository implements CodeRepositoryRepository, CodeRepositoryReadRepository {
    private static final String FILE_NAME = "repositories.json";
    private final FileStorage fileStorage;
    private final CodeRepositoryMapper mapper;

    private final ConcurrentHashMap<CodeRepositoryId, CodeRepository> codeRepositories = new ConcurrentHashMap<>();

    // Domain repository methods (for commands)
    @Override
    public void save(CodeRepository codeRepository) {
        codeRepositories.put(codeRepository.getId(), codeRepository);
    }

    @Override
    public void delete(CodeRepository codeRepository) {
        codeRepositories.remove(codeRepository.getId());
    }

    @Override
    public boolean exists(FullName fullName) {
        return codeRepositories.values().stream()
                .anyMatch(codeRepository -> codeRepository.getFullName().equals(fullName));
    }

    @Override
    public List<CodeRepository> findAll() {
        return List.copyOf(codeRepositories.values());
    }

    public Integer count() {
        return codeRepositories.size();
    }

    // Read repository methods (for queries)
    @Override
    public List<CodeRepositoryProjection> findAllAsViews() {
        return codeRepositories.values().stream().map(CodeRepositoryProjection::from).toList();
    }

    @Override
    public void initialize() {
        List<CodeRepositoryDto> loadedCodeRepositories = fileStorage.load(FILE_NAME, CodeRepositoryDto.class);

        codeRepositories.putAll(loadedCodeRepositories.stream().map(mapper::toDomain)
                .collect(Collectors.toMap(CodeRepository::getId, Function.identity())));
    }

    @Override
    public void persist() throws IOException {
        fileStorage.save(FILE_NAME, codeRepositories.values().stream().map(mapper::toDto).toList());
    }
}
