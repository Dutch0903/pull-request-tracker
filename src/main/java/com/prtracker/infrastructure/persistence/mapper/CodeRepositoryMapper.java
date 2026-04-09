package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryId;
import com.prtracker.domain.valueobject.FullName;
import com.prtracker.domain.valueobject.TokenId;
import com.prtracker.infrastructure.persistence.dto.CodeRepositoryDto;
import org.springframework.stereotype.Component;

@Component
public class CodeRepositoryMapper {
    public CodeRepositoryDto toDto(CodeRepository codeRepository) {
        return new CodeRepositoryDto(codeRepository.getId().value(), codeRepository.getFullName().owner(),
                codeRepository.getFullName().name(), codeRepository.getStatus().toString(),
                codeRepository.getTokenId() != null ? codeRepository.getTokenId().value() : null);
    }

    public CodeRepository toDomain(CodeRepositoryDto codeRepositoryDto) {
        return new CodeRepository(CodeRepositoryId.from(codeRepositoryDto.id()),
                new FullName(codeRepositoryDto.owner(), codeRepositoryDto.name()),
                CodeRepositoryStatus.valueOf(codeRepositoryDto.status()),
                codeRepositoryDto.tokenId() != null ? TokenId.from(codeRepositoryDto.tokenId()) : null);
    }
}
