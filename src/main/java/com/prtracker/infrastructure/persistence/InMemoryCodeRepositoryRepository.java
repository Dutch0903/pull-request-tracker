package com.prtracker.infrastructure.persistence;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import com.prtracker.domain.valueobject.CodeRepositoryId;
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
public class InMemoryCodeRepositoryRepository implements CodeRepositoryRepository {
    private static final String FILE_NAME = "repositories.json";
    private final FileStorage fileStorage;
    private final CodeRepositoryMapper mapper;

    private final ConcurrentHashMap<CodeRepositoryId, CodeRepository> codeRepositories = new ConcurrentHashMap<>();

    @Override
    public void save(CodeRepository codeRepository) {

    }

    @Override
    public void delete(CodeRepository codeRepository) {

    }

    @Override
    public List<CodeRepository> findAll() {
        return List.of();
    }

    public Integer count() {
        return codeRepositories.size();
    }

    @Override
    public void initialize() {
        List<CodeRepositoryDto> loadedCodeRepositories = fileStorage.load(FILE_NAME, CodeRepositoryDto.class);

        codeRepositories.putAll(
                loadedCodeRepositories.stream()
                        .map(mapper::toDomain)
                        .collect(
                                Collectors.toMap(
                                        CodeRepository::getId,
                                        Function.identity()
                                )
                        )
        );
    }

    @Override
    public void persist() throws IOException {
        fileStorage.save(FILE_NAME, codeRepositories.values());
    }
}
