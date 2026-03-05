package com.prtracker.infrastructure.persistence.mapper;

import com.prtracker.domain.entity.CodeRepository;
import com.prtracker.domain.enums.CodeRepositoryStatus;
import com.prtracker.domain.valueobject.CodeRepositoryIdentifier;
import com.prtracker.infrastructure.persistence.dto.CodeRepositoryDto;
import org.springframework.stereotype.Component;

@Component
public class CodeRepositoryMapper {
    public CodeRepositoryDto toDto(CodeRepository codeRepository) {
        return new CodeRepositoryDto(
                codeRepository.getIdentifier().value(),
                codeRepository.getOwner(),
                codeRepository.getName(),
                codeRepository.getUrl(),
                codeRepository.getStatus().toString(),
                codeRepository.getAccessToken()
        );
    }

    public CodeRepository toDomain(CodeRepositoryDto codeRepositoryDto) {
        return new CodeRepository(
                CodeRepositoryIdentifier.from(codeRepositoryDto.identifier()),
                codeRepositoryDto.owner(),
                codeRepositoryDto.name(),
                codeRepositoryDto.url(),
                CodeRepositoryStatus.valueOf(codeRepositoryDto.status()),
                codeRepositoryDto.accessToken()
        );
    }
}
