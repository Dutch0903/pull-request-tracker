package com.pullrequesttracker.infrastructure.persistence;

import com.pullrequesttracker.domain.model.CodeRepository;
import com.pullrequesttracker.domain.model.RepositoryAccess;
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
                codeRepository.getTokenId().map(TokenId::value).orElse(null),
                codeRepository.getLastCheckedAt().map(Instant::toString).orElse(null)
        );
    }

    public CodeRepository toDomain(CodeRepositoryDto dto) {
        RepositoryAccess access = dto.tokenId() != null
                ? new RepositoryAccess.Authenticated(new TokenId(dto.tokenId()))
                : new RepositoryAccess.Public();

        CodeRepository codeRepository = new CodeRepository(
                new CodeRepositoryId(dto.id()),
                new FullName(dto.owner(), dto.name()),
                Platform.valueOf(dto.platform()),
                access
        );

        if (dto.lastCheckedAt() != null) {
            codeRepository.recordChecked(Instant.parse(dto.lastCheckedAt()));
        }

        return codeRepository;
    }
}
