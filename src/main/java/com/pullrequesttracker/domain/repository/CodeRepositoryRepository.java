package com.pullrequesttracker.domain.repository;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.valueobject.FullName;

import java.util.List;

public interface CodeRepositoryRepository {
    void save(CodeRepository codeRepository);
    boolean exists(FullName fullName);
    List<CodeRepository> findAll();
}
