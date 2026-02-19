package com.prtracker.domain.repository;

import com.prtracker.domain.entity.CodeRepository;

import java.io.IOException;
import java.util.List;

public interface CodeRepositoryRepository {
    void save(CodeRepository codeRepository);

    void delete(CodeRepository codeRepository);

    List<CodeRepository> findAll();

    Integer count();

    void initialize() throws IOException;

    void persist() throws IOException;
}
