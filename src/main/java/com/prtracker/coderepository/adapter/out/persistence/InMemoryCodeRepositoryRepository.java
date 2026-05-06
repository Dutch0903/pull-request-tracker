package com.prtracker.coderepository.adapter.out.persistence;

import com.prtracker.coderepository.application.query.CodeRepositoryProjection;
import com.prtracker.coderepository.application.query.port.CodeRepositoryReadPort;
import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.FullName;
import com.prtracker.coderepository.domain.port.CodeRepositoryRepository;
import com.prtracker.shared.persistence.FileStorage;
import com.prtracker.shared.kernel.CodeRepositoryId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InMemoryCodeRepositoryRepository implements CodeRepositoryRepository, CodeRepositoryReadPort {
    private static final String FILE_NAME = "repositories.json";
    private final FileStorage fileStorage;
    private final CodeRepositoryMapper mapper;

    private final ConcurrentHashMap<CodeRepositoryId, CodeRepository> codeRepositories = new ConcurrentHashMap<>();

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

    @Override
    public Integer count() {
        return codeRepositories.size();
    }

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
