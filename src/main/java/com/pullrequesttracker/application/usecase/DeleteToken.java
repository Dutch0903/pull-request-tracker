package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.exception.DeleteTokenException;
import com.pullrequesttracker.domain.repository.TokenPersistenceException;
import com.pullrequesttracker.domain.repository.TokenRepository;
import com.pullrequesttracker.domain.valueobject.TokenId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteToken {
    private final TokenRepository tokenRepository;

    public void execute(TokenId id) {
        try {
            tokenRepository.delete(id);
        } catch (TokenPersistenceException e) {
            throw new DeleteTokenException(e.getMessage(), e);
        }
    }
}
