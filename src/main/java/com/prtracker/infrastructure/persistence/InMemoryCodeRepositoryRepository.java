package com.prtracker.infrastructure.persistence;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.repository.CodeRepositoryRepository;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
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

    private final ConcurrentHashMap<CodeRepositoryIdentifier, CodeRepository> codeRepositories = new ConcurrentHashMap<>();

    @Override
    public void save(CodeRepository codeRepository) {
        codeRepositories.put(codeRepository.getIdentifier(), codeRepository);
    }

    @Override
    public void delete(CodeRepository codeRepository) {
        codeRepositories.remove(codeRepository.getIdentifier());
    }

    @Override
    public boolean exists(CodeRepositoryIdentifier identifier) {
        return codeRepositories.containsKey(identifier);
    }

    @Override
    public List<CodeRepository> findAll() {
        return List.copyOf(codeRepositories.values());
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
                                        CodeRepository::getIdentifier,
                                        Function.identity()
                                )
                        )
        );
    }

    @Override
    public void persist() throws IOException {
        fileStorage.save(
                FILE_NAME,
                codeRepositories.values()
                        .stream()
                        .map(mapper::toDto)
                        .toList()
        );
    }
}
