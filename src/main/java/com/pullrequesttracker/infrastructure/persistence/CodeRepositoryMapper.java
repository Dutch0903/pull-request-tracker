package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.type.Platform;
import com.pullrequesttracker.domain.valueobject.CodeRepositoryId;
import com.pullrequesttracker.domain.valueobject.FullName;
import com.pullrequesttracker.domain.valueobject.TokenId;
import com.pullrequesttracker.infrastructure.persistence.dto.CodeRepositoryDto;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class CodeRepositoryMapper {

    public CodeRepositoryDto toDto(CodeRepository codeRepository) {
        return new CodeRepositoryDto(
                codeRepository.getId().value(),
                codeRepository.getFullName().owner(),
                codeRepository.getFullName().name(),
                codeRepository.getPlatform().name(),
                codeRepository.getTokenId() != null ? codeRepository.getTokenId().value() : null,
                codeRepository.getLastCheckedAt() != null ? codeRepository.getLastCheckedAt().toString() : null
        );
    }

    public CodeRepository toDomain(CodeRepositoryDto dto) {
        TokenId tokenId = dto.tokenId() != null ? new TokenId(dto.tokenId()) : null;
        CodeRepository codeRepository = new CodeRepository(
                new CodeRepositoryId(dto.id()),
                new FullName(dto.owner(), dto.name()),
                Platform.valueOf(dto.platform()),
                tokenId
        );

        if (dto.lastCheckedAt() != null) {
            codeRepository.recordChecked(Instant.parse(dto.lastCheckedAt()));
        }

        return codeRepository;
    }
}
