package com.prtracker.coderepository.domain.port;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.FullName;

import java.io.IOException;
import java.util.List;

public interface CodeRepositoryRepository {
    void save(CodeRepository codeRepository);

    void delete(CodeRepository codeRepository);

    boolean exists(FullName fullName);

    List<CodeRepository> findAll();

    Integer count();

    void initialize() throws IOException;

    void persist() throws IOException;
}
