package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.infrastructure.persistence.dto.CodeRepositoryDto;
import org.springframework.stereotype.Component;

@Component
public class CodeRepositoryMapper {
    public CodeRepositoryDto toDto(CodeRepository codeRepository) {
        return new CodeRepositoryDto(
                codeRepository.getId().value(),
                codeRepository.getOwner(),
                codeRepository.getName(),
                codeRepository.getUrl()
        );
    }

    public CodeRepository toDomain(CodeRepositoryDto codeRepositoryDto) {
        return new CodeRepository(
                CodeRepositoryId.from(codeRepositoryDto.id()),
                codeRepositoryDto.owner(),
                codeRepositoryDto.name(),
                codeRepositoryDto.url()
        );
    }
}
