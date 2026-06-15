package com.pullrequesttracker.application.usecase;

import com.pullrequesttracker.application.provider.TokenInfoProvider;
import com.pullrequesttracker.domain.repository.TokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateTokenTest {
    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private TokenInfoProvider tokenInfoProvider;

    @InjectMocks
    private CreateToken createToken;

    @Test
    void execute_whenCalled_shouldFetchTokenInfoAndSave() {

    }

    @Test
    void execute_whenTokenNameAlreadyExists_shouldThrowException() {

    }
}
