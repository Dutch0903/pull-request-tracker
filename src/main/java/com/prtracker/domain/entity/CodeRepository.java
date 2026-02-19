package com.prtracker.domain.entity;

import com.prtracker.domain.valueobject.CodeRepositoryId;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CodeRepository {
    private CodeRepositoryId id;
    private String owner;
    private String name;
    private String url;
}
