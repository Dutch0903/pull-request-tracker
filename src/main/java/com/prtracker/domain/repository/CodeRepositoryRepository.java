package com.prtracker.domain.repository;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;

import java.io.IOException;
import java.util.List;

public interface CodeRepositoryRepository {
    void save(CodeRepository codeRepository);

    void delete(CodeRepository codeRepository);

    boolean exists(CodeRepositoryIdentifier identifier);

    List<CodeRepository> findAll();

    Integer count();

    void initialize() throws IOException;

    void persist() throws IOException;
}
