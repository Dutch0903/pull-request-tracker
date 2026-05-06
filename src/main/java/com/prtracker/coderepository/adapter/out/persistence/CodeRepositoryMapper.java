package com.prtracker.coderepository.adapter.out.persistence;

import com.prtracker.coderepository.domain.model.CodeRepository;
import com.prtracker.coderepository.domain.model.CodeRepositoryStatus;
import com.prtracker.coderepository.domain.model.FullName;
import com.prtracker.shared.kernel.CodeRepositoryId;
import com.prtracker.shared.kernel.TokenId;
import org.springframework.stereotype.Component;

@Component
public class CodeRepositoryMapper {
    public CodeRepositoryDto toDto(CodeRepository codeRepository) {
        return new CodeRepositoryDto(codeRepository.getId().value(), codeRepository.getFullName().owner(),
                codeRepository.getFullName().name(), codeRepository.getStatus().toString(),
                codeRepository.getTokenId() != null ? codeRepository.getTokenId().value() : null);
    }

    public CodeRepository toDomain(CodeRepositoryDto dto) {
        return new CodeRepository(CodeRepositoryId.from(dto.id()),
                new FullName(dto.owner(), dto.name()),
                CodeRepositoryStatus.valueOf(dto.status()),
                dto.tokenId() != null ? TokenId.from(dto.tokenId()) : null);
    }
}
